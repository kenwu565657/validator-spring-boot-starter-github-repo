package com.validator.example.dto;

import java.util.List;

@SuppressWarnings("unused")
public class ValidationResultDto {
    private Boolean valid;
    private List<ValidationErrorDto> validationErrorList;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public List<ValidationErrorDto> getValidationErrorList() {
        return validationErrorList;
    }

    public void setValidationErrorList(List<ValidationErrorDto> validationErrorList) {
        this.validationErrorList = validationErrorList;
    }
}
