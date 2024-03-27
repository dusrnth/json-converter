package io.kyupid.jsonconvertor.json;

import java.util.List;
import java.util.Map;

abstract class JSON {

    // 적절한 자료구조로 return 한다.
    // 미리 정의한 객체로 return or Map ?
    // 아니다. 객체는 Map 으로, 배열은 List 로 반환하고,
    // 특정 데이터 타입이 필요하면 그건 캐스팅하는 애를 따로 두고 하자.
    protected abstract Object parse(String json);

    protected abstract Map<String, Object> parseObject();

    protected abstract List<Object> parseArray();

    protected abstract String parseString();

    protected abstract Number parseNumber();

    protected abstract Boolean parseBoolean();

    protected abstract Object parseNull();

    // index 활용해 현재 위치가 whitespace 이면 index++;
    protected abstract void skipWhitespace();

    // 해당 토큰에서 다음에 기대되는 char는 Token에 정의한다.
    protected abstract boolean expect(char expected);

    // 매 토큰마다 검사
    protected boolean isEndOfString() {
        return false;
    }

}
