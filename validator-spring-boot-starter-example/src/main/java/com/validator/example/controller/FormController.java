package com.validator.example.controller;

import com.validator.example.dto.ValidationResultDto;
import com.validator.example.form.SimpleForm;
import com.validator.example.mapper.ValidationMapper;
import com.validator.example.service.FormService;
import com.validator.pojo.ConstraintViolationWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Set;

@RestController
public class FormController {
    private final FormService formService;
    private final ValidationMapper validationMapper;

    public FormController(FormService formService, ValidationMapper validationMapper) {
        this.formService = formService;
        this.validationMapper = validationMapper;
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidationResultDto> validate(@RequestBody SimpleForm simpleForm) {
        Set<ConstraintViolationWrapper<SimpleForm>> constraintViolationWrapperSet = formService.validateForm(simpleForm, Locale.TRADITIONAL_CHINESE);
        var validationResultDto = validationMapper.toValidationResultDto(constraintViolationWrapperSet);
        if (validationResultDto.getValid()) {
            return ResponseEntity.ok(validationResultDto);
        } else {
            return ResponseEntity.badRequest().body(validationResultDto);
        }
    }
}
