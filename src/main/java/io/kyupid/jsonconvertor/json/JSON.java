package io.kyupid.jsonconvertor.json;

import io.kyupid.jsonconvertor.json.token.Token;

import java.util.List;
import java.util.Map;

public class JSON {

    private List<Token> tokens;

    public JSON(String json) {
        JSONTokenizer tokenizer = new JSONTokenizer(json);
        this.tokens = tokenizer.tokenize();
    }

    // 적절한 자료구조로 return 한다.
    // 미리 정의한 객체로 return or Map ?
    // 아니다. 객체는 Map 으로, 배열은 List 로 반환하고,
    // 특정 데이터 타입이 필요하면 그건 캐스팅하는 애를 따로 두고 하자.
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
