package io.kyupid.jsonconvertor.json.token;

import java.util.HashMap;
import java.util.Map;

public final class TokenUtils {
    private static final Map<Character, TokenType> CHAR_TO_TOKEN_TYPE = new HashMap<>();

    static {
        for (TokenType type : TokenType.values()) {
            CHAR_TO_TOKEN_TYPE.put(type.getChar(), type);
        }
    }

    private TokenUtils() {}

    public static TokenType getTokenType(char c) {
        return CHAR_TO_TOKEN_TYPE.get(c);
    }

    public static Token createToken(TokenType type, String value) {
        if (type == TokenType.STRING || type == TokenType.NUMBER) {
            return new Token(type, value);
        }
        // todo 나머지 타입 토큰들은 막을 것인가?
        return new Token(type, null);
    }
}