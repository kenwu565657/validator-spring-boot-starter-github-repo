package com.validator.example.form;

import com.validator.annotation.FieldName;
import com.validator.constant.LocaleConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@SuppressWarnings("unused")
@FieldName(value = "A Simple Form")
@FieldName(value = "簡單表格", locale = LocaleConstant.TRADITIONAL_CHINESE)
@FieldName(value = "简单表格", locale = LocaleConstant.SIMPLIFIED_CHINESE)
public class SimpleForm {
    @NotBlank
    @FieldName("Simple Form Id")
    @FieldName(value = "簡單表格點碼", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "简单表格点码", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String id;

    @NotBlank
    @FieldName("Contact First Name")
    @FieldName(value = "聯絡姓名 (名字)", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "联系姓名 (名字)", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String firstname;

    @NotBlank
    @FieldName("Contact Last Name")
    @FieldName(value = "聯絡姓名 (姓)", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "联系姓名 (姓)", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String lastname;

    @NotBlank
    @Email
    @FieldName("Contact Email Address")
    @FieldName(value = "聯絡電郵", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "联系电邮", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String emailAddress;

    @NotBlank
    @FieldName("Contact Phone Number")
    @FieldName(value = "聯絡電話號碼", locale = LocaleConstant.TRADITIONAL_CHINESE)
    @FieldName(value = "联系电话号码", locale = LocaleConstant.SIMPLIFIED_CHINESE)
    private String phoneNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
