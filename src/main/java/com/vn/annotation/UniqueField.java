package com.vn.annotation;

import com.vn.utils.validation.FieldValueExists;
import com.vn.utils.validation.UniqueFieldValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueFieldValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueField {
    String message() default "This field must be unique!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends FieldValueExists> service();

    String serviceQualifier() default "";

    String fieldName();
}
