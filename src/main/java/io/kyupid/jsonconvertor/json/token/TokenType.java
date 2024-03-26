package io.kyupid.jsonconvertor.json.token;

public enum TokenType {
    LEFT_BRACE('{'),
    RIGHT_BRACE('}'),
    DOUBLE_QUOTES('"'),
    LEFT_BRACKET('['),
    RIGHT_BRACKET(']'),
    COLON(':'),
    COMMA(','),
    TRUE('t'),
    FALSE('f'),
    NULL('n'),
    STRING('s'),
    NUMBER('0'),
    END_OF_STRING(Character.MIN_VALUE);

    private final char c;

    TokenType(char c) {
        this.c = c;
    }

    public char getChar() {
        return c;
    }
}
