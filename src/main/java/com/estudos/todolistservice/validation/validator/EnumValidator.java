package com.estudos.todolistservice.validation.validator;

import com.estudos.todolistservice.validation.annotation.EnumValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<EnumValid, Enum<?>> {
    private Set<String> valoresValidos;

    @Override
    public void initialize(EnumValid annotation) {
        valoresValidos = Arrays.stream(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return valoresValidos.contains(value.name());
    }
}
