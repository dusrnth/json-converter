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

    List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (pos < json.length()) {
            char currentChar = nextChar();
            TokenType tokenType = TokenUtils.getTokenType(currentChar);

            if (tokenType == null) {
                throw new InvalidJsonException("Invalid character: " + currentChar);
            }

            switch (tokenType) {
                case DOUBLE_QUOTES:
                    tokens.add(readString());
                    break;
                case LEFT_BRACE:
                case LEFT_BRACKET:
                case COMMA:
                case COLON:
                case RIGHT_BRACE:
                case RIGHT_BRACKET:
                    tokens.add(TokenUtils.createToken(tokenType, null));
                    break;
                default:
                    pos--;
                    tokens.add(readValue());
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

    private Token readValue() {
        char currentChar = json.charAt(pos);

        if (currentChar == '\"') {
            pos++;
            return readString();
        } else if (Character.isDigit(currentChar) || currentChar == '-') {
            return readNumber();
        } else if (currentChar == 't') {
            return readKeyword("true", TokenType.TRUE);
        } else if (currentChar == 'f') {
            return readKeyword("false", TokenType.FALSE);
        } else if (currentChar == 'n') {
            return readKeyword("null", TokenType.NULL);
        } else if (currentChar == '{') {
            return TokenUtils.createToken(TokenType.LEFT_BRACE, null);
        } else if (currentChar == '[') {
            return TokenUtils.createToken(TokenType.LEFT_BRACKET, null);
        } else {
            throw new InvalidJsonException("Unexpected character: " + currentChar);
        }
    }

    private Token readNumber() {
    }

    private Token readKeyword(String keyword, TokenType tokenType) {
        for (int i = 1; i < keyword.length(); i++) {
            if (nextChar() != keyword.charAt(i)) {
                throw new InvalidJsonException("Invalid keyword: " + keyword);
            }
        }
        return TokenUtils.createToken(tokenType, null);
    }
}