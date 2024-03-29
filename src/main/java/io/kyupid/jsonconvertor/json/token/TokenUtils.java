package io.kyupid.jsonconvertor.json.token;

import java.util.HashMap;
import java.util.Map;

public final class TokenUtils {
    private static final Map<Character, TokenType> CHAR_TO_TOKEN_TYPE = new HashMap<>();
    private static final Map<TokenType, Token> CACHED_TOKENS = new HashMap<>();

    static {
        for (TokenType type : TokenType.values()) {
            CHAR_TO_TOKEN_TYPE.put(type.getSymbol(), type);
        }

        // 기본 타입 토큰만 캐시 처리
        CACHED_TOKENS.put(TokenType.LEFT_BRACE, new Token(TokenType.LEFT_BRACE, null));
        CACHED_TOKENS.put(TokenType.RIGHT_BRACE, new Token(TokenType.RIGHT_BRACE, null));
        CACHED_TOKENS.put(TokenType.LEFT_BRACKET, new Token(TokenType.LEFT_BRACKET, null));
        CACHED_TOKENS.put(TokenType.RIGHT_BRACKET, new Token(TokenType.RIGHT_BRACKET, null));
        CACHED_TOKENS.put(TokenType.COLON, new Token(TokenType.COLON, null));
        CACHED_TOKENS.put(TokenType.COMMA, new Token(TokenType.COMMA, null));
    }

    private TokenUtils() {}

    public static TokenType getTokenType(char c) {
        return CHAR_TO_TOKEN_TYPE.get(c);
    }

    public static Token createToken(TokenType type, String value) {
        if (CACHED_TOKENS.containsKey(type)) {
            return CACHED_TOKENS.get(type);
        }
        return new Token(type, value);
    }
}