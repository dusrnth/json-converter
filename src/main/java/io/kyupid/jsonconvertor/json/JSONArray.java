package io.kyupid.jsonconvertor.json;

import java.util.List;
import java.util.Map;

public class JSONArray extends JSON {
    @Override
    protected Object parse(String json) {
        return null;
    }

    @Override
    protected Map<String, Object> parseObject() {
        return null;
    }

    @Override
    protected List<Object> parseArray() {
        return null;
    }

    @Override
    protected String parseString() {
        return null;
    }

    @Override
    protected Number parseNumber() {
        return null;
    }

    @Override
    protected Boolean parseBoolean() {
        return null;
    }

    @Override
    protected Object parseNull() {
        return null;
    }

    @Override
    protected void skipWhitespace() {

    }

    @Override
    protected boolean expect(char expected) {
        return false;
    }
}
