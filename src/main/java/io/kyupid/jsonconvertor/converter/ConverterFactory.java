package io.kyupid.jsonconvertor.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ConverterFactory {

    private final Map<String, Converter<?>> converters;

    @Autowired
    public ConverterFactory(List<Converter<?>> converters) {
        this.converters = converters.stream()
                .collect(Collectors.toMap(converter -> {
                    Component component = converter.getClass().getAnnotation(Component.class);
                    if (component == null) throw new IllegalStateException("Component annotation missing on converter class");
                    return component.value();
                },
                        Function.identity()));
    }

    @SuppressWarnings("unchecked")
    public <T> Converter<T> getConverter(ConverterType converterType) {
        Converter<?> converter = converters.get(converterType.getConverterName());
        if (converter == null) throw new IllegalArgumentException("No converter found for type: " + converterType);
        return (Converter<T>) converter;
    }
}