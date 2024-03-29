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
            char currentChar = json.charAt(pos);

            if (currentChar == '{') {
                tokens.add(new Token(TokenType.LEFT_BRACE, null, null));
                pos++;
            } else if (currentChar == '}') {
                tokens.add(new Token(TokenType.RIGHT_BRACE, null, null));
                pos++;
            } else if (currentChar == '[') {
                tokens.add(new Token(TokenType.LEFT_BRACKET, null, null));
                pos++;
            } else if (currentChar == ']') {
                tokens.add(new Token(TokenType.RIGHT_BRACKET, null, null));
                pos++;
            } else if (currentChar == ':') {
                tokens.add(new Token(TokenType.COLON, null, null));
                pos++;
            } else if (currentChar == ',') {
                tokens.add(new Token(TokenType.COMMA, null, null));
                pos++;
            } else if (currentChar == '"') {
                pos++;
                Token token = readString();
                tokens.add(token);
            } else if (Character.isDigit(currentChar) || currentChar == '-') {
                Token token = readNumber();
                tokens.add(token);
            } else if (currentChar == 't' || currentChar == 'f') {
                Token token = readBoolean();
                tokens.add(token);
            } else if (currentChar == 'n') {
                Token token = readNull();
                tokens.add(token);
            } else if (Character.isWhitespace(currentChar)) {
                pos++;
            } else {
                throw new InvalidJsonException("Unexpected character: " + currentChar);
            }
        }

        return tokens;
    }

    private Token readString() {
        StringBuilder sb = new StringBuilder();
        char currentChar = json.charAt(pos);

        while (currentChar != '"') {
            if (currentChar == '\\') {
                pos++;
                currentChar = json.charAt(pos);
                // 이스케이프 문자 처리 로직 추가
            }
            sb.append(currentChar);
            pos++;
            if (pos >= json.length()) {
                throw new InvalidJsonException("Unterminated string");
            }
            currentChar = json.charAt(pos);
        }
        pos++;
        return new Token(TokenType.STRING, null, sb.toString());
    }

    private Token readNumber() {
        StringBuilder sb = new StringBuilder();
        char currentChar = json.charAt(pos);

        while (Character.isDigit(currentChar) || currentChar == '.' || currentChar == 'e' || currentChar == 'E' || currentChar == '-' || currentChar == '+') {
            sb.append(currentChar);
            pos++;
            if (pos >= json.length()) {
                break;
            }
            currentChar = json.charAt(pos);
        }

        return new Token(TokenType.NUMBER, null, sb.toString());
    }

    private Token readBoolean() {
        if (json.startsWith("true", pos)) {
            pos += 4;
            return new Token(TokenType.BOOLEAN, null, "true");
        } else if (json.startsWith("false", pos)) {
            pos += 5;
            return new Token(TokenType.BOOLEAN, null, "false");
        } else {
            throw new InvalidJsonException("Invalid boolean value");
        }
    }

    private Token readNull() {
        if (json.startsWith("null", pos)) {
            pos += 4;
            return new Token(TokenType.NULL, null, "null");
        } else {
            throw new InvalidJsonException("Invalid null value");
        }
    }
}