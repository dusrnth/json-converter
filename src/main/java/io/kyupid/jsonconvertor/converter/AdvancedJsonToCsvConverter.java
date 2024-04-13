package io.kyupid.jsonconvertor.converter;

import io.kyupid.jsonconvertor.model.CSV;

public class AdvancedJsonToCsvConverter implements Converter<CSV> {
    private static final AdvancedJsonToCsvConverter converter = new AdvancedJsonToCsvConverter();


    private AdvancedJsonToCsvConverter() {
    }

    public static AdvancedJsonToCsvConverter getInstance() {
        return converter;
    }


    @Override
    public CSV convert(String jsonString) {
        return null;
    }
}