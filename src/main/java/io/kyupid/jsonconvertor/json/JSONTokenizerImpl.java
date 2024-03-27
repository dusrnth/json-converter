package io.kyupid.jsonconvertor.json;

class JSONTokenizerImpl implements JSONTokenizer {
    private final String json;
    private final int pos;

    public JSONTokenizerImpl(String json) {
        this.json = json;
        this.pos = 0;
    }

}