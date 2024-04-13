package io.kyupid.jsonconvertor.converter;

public interface Converter<V> {
    V convert(String jsonString);
}