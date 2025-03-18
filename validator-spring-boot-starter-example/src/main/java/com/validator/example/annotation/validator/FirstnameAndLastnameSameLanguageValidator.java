package com.validator.example.annotation.validator;

import com.validator.example.annotation.ValidateFirstnameAndLastnameSameLanguage;
import com.validator.example.form.ExampleForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FirstnameAndLastnameSameLanguageValidator implements ConstraintValidator<ValidateFirstnameAndLastnameSameLanguage, ExampleForm> {
    @Override
    public boolean isValid(ExampleForm exampleForm, ConstraintValidatorContext context) {
        if (isBlank(exampleForm.getFirstname()) || isBlank(exampleForm.getLastname())) {
            return true;
        }
        if (isContainingEnglishAndSpaceOnly(exampleForm.getFirstname())) {
            return isContainingEnglishAndSpaceOnly(exampleForm.getLastname());
        }
        if (isChinese(exampleForm.getFirstname())) {
            return isChinese(exampleForm.getLastname());
        }
        return false;
    }

    private boolean isBlank(String string) {
        if (null == string || string.isBlank()) {
            return true;
        }
        return false;
    }

    private boolean isContainingEnglishAndSpaceOnly(String string) {
        return string.matches("^[a-zA-Z][a-zA-Z\\s]+$");
    }

    private boolean isChinese(String string) {
        return string.matches("^[\u4E00-\u9FA5]+$");
    }
}
