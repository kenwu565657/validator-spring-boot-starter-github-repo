package com.validator.example.dto;

@SuppressWarnings("unused")
public class ValidationErrorDto {
    private String errorField;
    private String errorMessage;

    public String getErrorField() {
        return errorField;
    }

    public void setErrorField(String errorField) {
        this.errorField = errorField;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
