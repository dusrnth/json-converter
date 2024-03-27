package io.kyupid.jsonconvertor.util;

import io.kyupid.jsonconvertor.exception.InvalidJsonException;

public class DataValidator {
    public static void validateJsonData(JsonData jsonData) throws InvalidJsonException {
        if (jsonData == null) {
            throw new InvalidJsonException("JSON data cannot be null");
        }

        if (jsonData.getField1() == null || jsonData.getField1().isEmpty()) {
            throw new InvalidJsonException("Field1 is required");
        }
    }
}
