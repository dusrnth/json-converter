package io.kyupid.jsonconvertor.json.token;

public final class Token {
    private final TokenType type;
    private final String property;
    private final String value;

    public Token(TokenType type, String property, String value) {
        this.type = type;
        this.property = property;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }
}
