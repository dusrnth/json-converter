package io.kyupid.jsonconvertor.json;

import io.kyupid.jsonconvertor.exception.InvalidJsonException;
import io.kyupid.jsonconvertor.json.token.Token;

import java.util.List;
import java.util.Map;

public class JSON {

    private List<Token> tokens;

    public JSON(String json) throws InvalidJsonException {
        JSONTokenizer tokenizer = new JSONTokenizer(json);
        this.tokens = tokenizer.tokenize();
    }

    // 객체는 Map 으로, 배열은 List 로 반환하고,
    public Object parse() {

        return null;
    }

    private Map<String, Object> parseObject() {
        return null;
    }

    private List<Object> parseArray() {
        return null;
    }

    private String parseString() {
        return null;
    }

    private Number parseNumber() {
        return null;
    }

    private Boolean parseBoolean() {
        return null;
    }

    private Object parseNull() {
        return null;
    }

    // index 활용해 현재 위치가 whitespace 이면 index++;
    private void skipWhitespace() {
    }

    // 해당 토큰에서 다음에 기대되는 char는 Token에 정의한다.
    private boolean expect(char expected) {
        return false;
    }

    // 매 토큰마다 검사
    private boolean isEndOfString() {
        return false;
    }
}
