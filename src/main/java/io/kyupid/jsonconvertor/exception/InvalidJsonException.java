package io.kyupid.jsonconvertor.exception;

public class InvalidJsonException extends RuntimeException {
    public InvalidJsonException(String message) {
        super(message);
    }
}
