package io.kyupid.jsonconvertor.json.token;

public final class Token {
    private final TokenType type;
    private final String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public boolean isEndOfString() {
        return type == TokenType.END_OF_STRING;
    }
}
