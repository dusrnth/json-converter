package io.kyupid.jsonconvertor.converter;

import io.kyupid.jsonconvertor.json.JSON;
import io.kyupid.jsonconvertor.model.CSV;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleJsonToCsvConverter implements Converter<CSV> {

    private static final SimpleJsonToCsvConverter converter = new SimpleJsonToCsvConverter();


    private SimpleJsonToCsvConverter() {
    }

    public static SimpleJsonToCsvConverter getInstance() {
        return converter;
    }

    @Override
    public CSV convert(String jsonString) {
        JSON json = new JSON(jsonString);
        Object parsedJson = json.parse();

        if (parsedJson instanceof List<?> jsonArray) {
            return convertJsonArrayToCsv(jsonArray);
        } else if (parsedJson instanceof Map) {
            return convertJsonMapToCsv((Map<String, Object>) parsedJson);
        }

        throw new IllegalArgumentException("지원되지 않는 JSON 형식입니다.");
    }

    private static CSV convertJsonArrayToCsv(List<?> jsonArray) {
        if (jsonArray.isEmpty()) {
            return new CSV();
        }

        Set<String> headers = extractHeaders(jsonArray);
        CSV csv = new CSV(headers);
        jsonArray.stream()
                .filter(item -> item instanceof Map)
                .forEach(item -> addJsonMapToCsvRow((Map<String, Object>) item, csv));

        return csv;
    }

    private static CSV convertJsonMapToCsv(Map<String, Object> jsonMap) {
        if (jsonMap.isEmpty()) {
            return new CSV();
        }

        Set<String> headers = extractHeaders(jsonMap);
        CSV csv = new CSV(headers);
        addJsonMapToCsvRow(jsonMap, csv);

        return csv;
    }

    private static Set<String> extractHeaders(List<?> jsonArray) {
        return jsonArray.stream()
                .filter(item -> item instanceof Map)
                .flatMap(item -> extractHeaders((Map<String, Object>) item).stream())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static Set<String> extractHeaders(Map<String, Object> jsonMap) {
        Set<String> headers = new LinkedHashSet<>();
        extractHeaders(jsonMap, "", headers);
        return headers;
    }

    private static void extractHeaders(Map<String, Object> map, String prefix, Set<String> headers) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String headerKey = prefix.isEmpty() ? key : prefix + "." + key;

            if (value instanceof Map) {
                extractHeaders((Map<String, Object>) value, headerKey, headers);
            } else {
                headers.add(headerKey);
            }
        }
    }

    private static void addJsonMapToCsvRow(Map<String, Object> jsonMap, CSV csv) {
        List<String> row = csv.getHeaders().stream()
                .map(header -> {
                    String[] keys = header.split("\\.");
                    Object value = getNestedValue(jsonMap, keys, 0);
                    return value != null ? value.toString() : "";
                })
                .toList();

        csv.addRow(row);
    }

    private static Object getNestedValue(Map<String, Object> map, String[] keys, int index) {
        if (index == keys.length - 1) {
            return map.get(keys[index]);
        }

        Object value = map.get(keys[index]);
        if (value instanceof Map) {
            return getNestedValue((Map<String, Object>) value, keys, index + 1);
        }

        return null;
    }
}