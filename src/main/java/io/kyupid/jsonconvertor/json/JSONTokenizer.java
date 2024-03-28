package io.kyupid.jsonconvertor.json;

import io.kyupid.jsonconvertor.exception.InvalidJsonException;
import io.kyupid.jsonconvertor.json.token.Token;
import io.kyupid.jsonconvertor.json.token.TokenType;
import io.kyupid.jsonconvertor.json.token.TokenUtils;

import java.util.ArrayList;
import java.util.List;

class JSONTokenizer {
    private final String json;
    private int pos;

    JSONTokenizer(String json) {
        this.json = json;
        this.pos = 0;
    }

    List<Token> tokenize() throws InvalidJsonException {
        List<Token> tokens = new ArrayList<>();

        while (pos < json.length()) {
            char currentChar = nextChar();
            TokenType tokenType = TokenUtils.getTokenType(currentChar);

            if (tokenType == null) {
                throw new InvalidJsonException("Invalid character: " + currentChar);
            }

            switch (tokenType) {
                case STRING:
                    tokens.add(readString());
                    break;
                case NUMBER:
                    tokens.add(readNumber());
                    break;
                case TRUE:
                    tokens.add(readKeyword("true", TokenType.TRUE));
                    break;
                case FALSE:
                    tokens.add(readKeyword("false", TokenType.FALSE));
                    break;
                case NULL:
                    tokens.add(readKeyword("null", TokenType.NULL));
                    break;
                default:
                    tokens.add(TokenUtils.createToken(tokenType, null));
                    break;
            }
        }

        tokens.add(TokenUtils.createToken(TokenType.END_OF_STRING, null));
        return tokens;
    }

    private char nextChar() {
        return json.charAt(pos++);
    }

    private Token readString() {
        StringBuilder sb = new StringBuilder();
        char currentChar = nextChar();

        while (currentChar != '\"') {
            if (currentChar == '\\') {
                currentChar = processEscapeChar();
            }
            sb.append(currentChar);
            currentChar = nextChar();
        }

        return TokenUtils.createToken(TokenType.STRING, sb.toString());
    }

    private char processEscapeChar() {
    }

    private Token readNumber() {
    }

    private Token readKeyword(String keyword, TokenType tokenType) throws InvalidJsonException {
        for (int i = 1; i < keyword.length(); i++) {
            if (nextChar() != keyword.charAt(i)) {
                throw new InvalidJsonException("Invalid keyword: " + keyword);
            }
        }
        return TokenUtils.createToken(tokenType, null);
    }
}