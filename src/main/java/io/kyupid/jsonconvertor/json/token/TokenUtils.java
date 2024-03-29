package io.kyupid.jsonconvertor.json.token;

import java.util.HashMap;
import java.util.Map;

public final class TokenUtils {
    private static final Map<Character, TokenType> CHAR_TO_TOKEN_TYPE = new HashMap<>();
    private static final Map<TokenType, Token> CACHED_TOKENS = new HashMap<>();

    static {
        CHAR_TO_TOKEN_TYPE.put('{', TokenType.LEFT_BRACE);
        CHAR_TO_TOKEN_TYPE.put('}', TokenType.RIGHT_BRACE);
        CHAR_TO_TOKEN_TYPE.put('[', TokenType.LEFT_BRACKET);
        CHAR_TO_TOKEN_TYPE.put(']', TokenType.RIGHT_BRACKET);
        CHAR_TO_TOKEN_TYPE.put(':', TokenType.COLON);
        CHAR_TO_TOKEN_TYPE.put(',', TokenType.COMMA);
        CHAR_TO_TOKEN_TYPE.put('"', TokenType.STRING);

        CACHED_TOKENS.put(TokenType.LEFT_BRACE, new Token(TokenType.LEFT_BRACE, null, null));
        CACHED_TOKENS.put(TokenType.RIGHT_BRACE, new Token(TokenType.RIGHT_BRACE, null, null));
        CACHED_TOKENS.put(TokenType.LEFT_BRACKET, new Token(TokenType.LEFT_BRACKET, null, null));
        CACHED_TOKENS.put(TokenType.RIGHT_BRACKET, new Token(TokenType.RIGHT_BRACKET, null, null));
        CACHED_TOKENS.put(TokenType.COLON, new Token(TokenType.COLON, null, null));
        CACHED_TOKENS.put(TokenType.COMMA, new Token(TokenType.COMMA, null, null));
    }

    private TokenUtils() {}

    public static TokenType getTokenType(char c) {
        return CHAR_TO_TOKEN_TYPE.get(c);
    }

    public static Token createToken(TokenType type, String property, String value) {
        if (CACHED_TOKENS.containsKey(type)) {
            return CACHED_TOKENS.get(type);
        }
        return new Token(type, property, value);
    }
}