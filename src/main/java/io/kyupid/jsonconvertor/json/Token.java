package io.kyupid.jsonconvertor.json;

public enum Token {
    LEFT_BRACE("{"),
    RIGHT_BRACE("}"),
    DOUBLE_QUOTES("\""),
    LEFT_BRACKET("["),
    RIGHT_BRACKET("]"),
    COLON(":"),
    COMMA(","),
    TRUE("true"),
    FALSE("false"),
    NULL("null"),
    STRING(null),
    NUMBER(null),
    END_OF_STRING(null);

    private String value;

    Token(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (this == STRING || this == NUMBER || this == END_OF_STRING) {
            this.value = value;
        } else {
            throw new IllegalStateException("Cannot set value for token: " + this);
        }
    }
}