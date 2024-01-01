package com.vn.utils.validation;

import org.springframework.stereotype.Component;

@Component
public interface FieldValueExists {
    boolean fieldValueExists(String fieldName, Object value);
}
