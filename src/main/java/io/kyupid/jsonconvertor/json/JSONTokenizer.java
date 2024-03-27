package io.kyupid.jsonconvertor.json;

import io.kyupid.jsonconvertor.json.token.Token;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class JSONTokenizer {
    private final String json;
    private final int pos;

    JSONTokenizer(String json) {
        this.json = json;
        this.pos = 0;
    }

    List<Token> tokenize() {
        // String json -> List<Token>
        return new LinkedList<>();
    }
}