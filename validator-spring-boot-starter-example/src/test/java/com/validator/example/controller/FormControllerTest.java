package com.validator.example.controller;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class FormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class TestValidate {
        @Test
        void testValidateOkResponse() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/validate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                            """
                            {
                                "id": "testingId",
                                "firstname": "testingFirstname",
                                "lastname": "testingLastname",
                                "emailAddress": "testingEmailAddress@gmail.com",
                                "phoneNumber": "testingPhoneNumber"
                            }
                            """
                            )
                    )
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().json(
                            """
                                    {
                                        "valid": true,
                                        "validationErrorList": []
                                    }
                                    """
                    ));

        }

        @Test
        void testValidateBadRequestAllBlankError() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/validate").contentType(MediaType.APPLICATION_JSON).content("{}"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().json(
                            """
                                    {
                                            "valid": false,
                                            "validationErrorList": [
                                                {
                                                    "errorField": "簡單表格 - 聯絡姓名 (名字)",
                                                    "errorMessage": "不得空白"
                                                },
                                                {
                                                    "errorField": "簡單表格 - 聯絡電郵",
                                                    "errorMessage": "不得空白"
                                                },
                                                {
                                                    "errorField": "簡單表格 - 聯絡姓名 (姓)",
                                                    "errorMessage": "不得空白"
                                                },
                                                {
                                                    "errorField": "簡單表格 - 簡單表格點碼",
                                                    "errorMessage": "不得空白"
                                                },
                                                {
                                                    "errorField": "簡單表格 - 聯絡電話號碼",
                                                    "errorMessage": "不得空白"
                                                }
                                            ]
                                        }
                                    """
                    ));

        }

        @Test
        void testValidateBadRequestEmailError() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/validate")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                            """
                            {
                                "id": "testingId",
                                "firstname": "testingFirstname",
                                "lastname": "testingLastname",
                                "emailAddress": "testingEmailAddress",
                                "phoneNumber": "testingPhoneNumber"
                            }
                            """
                            )
                    )
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().json(
                            """
                                    {
                                        "valid": false,
                                        "validationErrorList": [
                                            {
                                                "errorField": "簡單表格 - 聯絡電郵",
                                                "errorMessage": "必須是形式完整的電子郵件位址"
                                            }
                                        ]
                                    }
                                    """
                    ));
        }
    }
}
