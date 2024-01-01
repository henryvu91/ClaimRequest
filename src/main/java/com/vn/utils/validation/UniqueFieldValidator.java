package com.vn.utils.validation;

import com.vn.annotation.UniqueField;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueFieldValidator implements ConstraintValidator<UniqueField, Object> {

    private final ApplicationContext applicationContext;
    private FieldValueExists service;
    private String fieldName;

    @Autowired
    public UniqueFieldValidator(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void initialize(UniqueField unique) {
        Class<? extends FieldValueExists> serviceClass = unique.service();
        this.fieldName = unique.fieldName();
        String serviceQualifier = unique.serviceQualifier();

        //Check if the service is in the application context
        if (applicationContext == null) {
            throw new IllegalStateException("ApplicationContext has not been set in the validator.");
        }

        try {
            if (!serviceQualifier.isEmpty()) {
                this.service = applicationContext.getBean(serviceQualifier, serviceClass);
            } else {
                this.service = applicationContext.getBean(serviceClass);
            }
        } catch (NoSuchBeanDefinitionException e) {
            throw new IllegalStateException("Could not find an implementation of the FieldValueExists interface: " +
                    serviceClass.getName() + (serviceQualifier.isEmpty() ? "" : " with qualifier " + serviceQualifier), e);
        }

        if (this.service == null) {
            throw new IllegalStateException("A bean of type " + serviceClass.getName() + " has not been found in the application context.");
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return !this.service.fieldValueExists(fieldName, value);
    }
}
