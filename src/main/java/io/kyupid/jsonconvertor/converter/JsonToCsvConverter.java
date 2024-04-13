package io.kyupid.jsonconvertor.converter;

import io.kyupid.jsonconvertor.model.CSV;

public interface JsonToCsvConverter {
    CSV convert(String jsonString);
}