package io.kyupid.jsonconvertor.json;

public enum TokenType {
    LEFT_BRACE("{"),
    RIGHT_BRACE("}"),
    LEFT_BRACKET("["),
    RIGHT_BRACKET("]"),
    COLON(":"),
    COMMA(","),
    STRING,
    NUMBER,
    TRUE("true"),
    FALSE("false"),
    NULL("null"),
    END_OF_STRING;

    private final String value;

    TokenType() {
        this.value = null;
    }

    TokenType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}