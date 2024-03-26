package io.kyupid.jsonconvertor.json;

import java.util.*;

public final class Token {

    private static final Map<TokenType, Token> FIXED_TOKENS = new EnumMap<>(TokenType.class);

    static {
        for (TokenType type : TokenType.values()) {
            if (type != TokenType.STRING && type != TokenType.NUMBER) {
                FIXED_TOKENS.put(type, new Token(type, null));
            }
        }
    }

    private final TokenType type;
    private final String value;

    private Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public static Token of(TokenType type, String value) {
        if (type == TokenType.STRING || type == TokenType.NUMBER) {
            return new Token(type, value);
        }
        throw new IllegalArgumentException("Only STRING and NUMBER tokens can have a value");
    }

    public static Token of(TokenType type) {
        Token token = FIXED_TOKENS.get(type);
        if (token != null) {
            return token;
        }
        // todo string, number 의 처리 ?
        throw new IllegalArgumentException("Invalid token type: " + type);
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public enum TokenType {
        LEFT_BRACE,
        RIGHT_BRACE,
        DOUBLE_QUOTES,
        LEFT_BRACKET,
        RIGHT_BRACKET,
        COLON,
        COMMA,
        TRUE,
        FALSE,
        NULL,
        STRING,
        NUMBER
    }
}
