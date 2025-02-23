package com.validator.example.mapper;

import com.validator.example.dto.ValidationErrorDto;
import com.validator.example.dto.ValidationResultDto;
import com.validator.pojo.ConstraintViolationWrapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ValidationMapper {

    public <T> ValidationResultDto toValidationResultDto(Set<ConstraintViolationWrapper<T>> constraintViolationWrapperSet) {
        var validationResultDto = new ValidationResultDto();
        if (constraintViolationWrapperSet == null || constraintViolationWrapperSet.isEmpty()) {
            validationResultDto.setValid(true);
            validationResultDto.setValidationErrorList(List.of());
        } else {
            validationResultDto.setValid(false);
            List<ValidationErrorDto> validationErrorDtoList = constraintViolationWrapperSet.stream().map(this::toValidationErrorDto).toList();
            validationResultDto.setValidationErrorList(validationErrorDtoList);
        }
        return validationResultDto;
    }

    private ValidationErrorDto toValidationErrorDto(ConstraintViolationWrapper<?> constraintViolationWrapper) {
        ValidationErrorDto validationErrorDto = new ValidationErrorDto();
        validationErrorDto.setErrorField(constraintViolationWrapper.getTranslatedErrorFieldName());
        validationErrorDto.setErrorMessage(constraintViolationWrapper.getTranslatedMessage());
        return validationErrorDto;
    }
}
