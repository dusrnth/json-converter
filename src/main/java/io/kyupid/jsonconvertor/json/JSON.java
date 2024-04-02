package io.kyupid.jsonconvertor.json;

import io.kyupid.jsonconvertor.exception.InvalidJsonException;
import io.kyupid.jsonconvertor.json.token.Token;
import io.kyupid.jsonconvertor.json.token.TokenType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
public class JSON {
    private final List<Token> tokens;

    public JSON(String json) {
        JSONTokenizer tokenizer = new JSONTokenizer(json);
        this.tokens = tokenizer.tokenize();
    }

    public Object parse() {
        return parseValue();
    }

    private Object parseValue() {
        Token currentToken = tokens.get(0);

        switch (currentToken.getType()) {
            case LEFT_BRACE:
                return parseObject();
            case LEFT_BRACKET:
                return parseArray();
            case STRING:
                tokens.remove(0);
                return currentToken.getValue();
            case NUMBER:
                tokens.remove(0);
                return parseNumber(currentToken.getValue());
            case BOOLEAN:
                tokens.remove(0);
                return parseBoolean(currentToken.getValue());
            case NULL:
                tokens.remove(0);
                return null;
            default:
                throw new InvalidJsonException("Unexpected token: " + currentToken.getType());
        }
    }

    private Map<String, Object> parseObject() {
        Map<String, Object> object = new LinkedHashMap<>();
        tokens.remove(0); // Remove LEFT_BRACE

        while (!tokens.isEmpty()) {
            Token currentToken = tokens.get(0);

            if (currentToken.getType() == TokenType.RIGHT_BRACE) {
                tokens.remove(0);
                break;
            }

            if (currentToken.getType() != TokenType.STRING) {
                throw new InvalidJsonException("Expected string key, found: " + currentToken.getType());
            }

            String key = currentToken.getValue();
            tokens.remove(0);

            if (tokens.isEmpty() || tokens.get(0).getType() != TokenType.COLON) {
                throw new InvalidJsonException("Expected colon after key");
            }
            tokens.remove(0);

            Object value = parseValue();
            object.put(key, value);

            if (!tokens.isEmpty() && tokens.get(0).getType() == TokenType.COMMA) {
                tokens.remove(0);
            }
        }

        return object;
    }

    private List<Object> parseArray() {
        List<Object> array = new ArrayList<>();
        tokens.remove(0); // Remove LEFT_BRACKET

        while (!tokens.isEmpty()) {
            Token currentToken = tokens.get(0);

            if (currentToken.getType() == TokenType.RIGHT_BRACKET) {
                tokens.remove(0);
                break;
            }

            Object value = parseValue();
            array.add(value);

            if (!tokens.isEmpty() && tokens.get(0).getType() == TokenType.COMMA) {
                tokens.remove(0);
            }
        }

        return array;
    }

    private Number parseNumber(String value) {
        try {
            if (value.contains(".") || value.contains("e") || value.contains("E")) {
                return Double.parseDouble(value);
            } else {
                return Long.parseLong(value);
            }
        } catch (NumberFormatException e) {
            throw new InvalidJsonException("Invalid number format: " + value);
        }
    }

    private Boolean parseBoolean(String value) {
        if (value.equals("true")) {
            return true;
        } else if (value.equals("false")) {
            return false;
        } else {
            throw new InvalidJsonException("Invalid boolean value: " + value);
        }
    }
}