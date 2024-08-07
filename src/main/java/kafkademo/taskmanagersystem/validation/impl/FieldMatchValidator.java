package kafkademo.taskmanagersystem.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kafkademo.taskmanagersystem.dto.user.request.RegisterUserRequestDto;
import kafkademo.taskmanagersystem.validation.FieldMatch;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
            Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
            return Objects.equals(fieldValue, fieldMatchValue);
        } catch (Exception e) {
            return false;
        }
    }
}
