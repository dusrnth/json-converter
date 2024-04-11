package io.kyupid.jsonconvertor.converter;

import io.kyupid.jsonconvertor.json.JSON;
import io.kyupid.jsonconvertor.model.CSV;

import java.util.*;

public class JsonToCsvConverter {
    public static CSV convert(String jsonString) {
        JSON json = new JSON(jsonString);
        Object parsedJson = json.parse();

        if (parsedJson instanceof List<?> jsonArray) {
            return convertJsonArrayToCsv(jsonArray);
        } else if (parsedJson instanceof Map) {
            return convertJsonMapToCsv((Map<String, Object>) parsedJson);
        }

        throw new IllegalArgumentException("지원되지 않는 JSON 형식입니다.");
    }

    // JSON 배열을 CSV로 변환하는 메서드
    private static CSV convertJsonArrayToCsv(List<?> jsonArray) {
        if (jsonArray.isEmpty()) {
            return new CSV();
        }

        Set<String> headers = getHeaders(jsonArray);
        CSV csv = new CSV(headers);
        for (Object item : jsonArray) {
            if (item instanceof Map) {
                addJsonMapToCsvRow((Map<String, Object>) item, csv);
            }
        }

        return csv;
    }


    // JSON 객체를 CSV로 변환하는 메서드
    private static CSV convertJsonMapToCsv(Map<String, Object> jsonMap) {
        if (jsonMap.isEmpty()) {
            return new CSV();
        }
        Set<String> headers = new LinkedHashSet<>();
        extractHeaders(jsonMap, "", headers);

        CSV csv = new CSV(headers);
        addJsonMapToCsvRow(jsonMap, csv);
        return csv;
    }

    private static Set<String> getHeaders(List<?> jsonArray) {
        Set<String> headers = new LinkedHashSet<>();
        for (Object item : jsonArray) {
            if (item instanceof Map) {
                extractHeaders((Map<String, Object>) item, "", headers);
            }
        }
        return headers;
    }

    private static void extractHeaders(Map<String, Object> map, String prefix, Set<String> headers) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = prefix + entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                extractHeaders((Map<String, Object>) value, key + ".", headers);
            } else {
                headers.add(key);
            }
        }
    }

    // JSON Map을 CSV의 행으로 추가하는 메서드
    private static void addJsonMapToCsvRow(Map<String, Object> jsonMap, CSV csv) {
        List<String> row = new ArrayList<>();
        for (String header : csv.getHeaders()) {
            String[] keys = header.split("\\.");
            Object value = getNestedValue(jsonMap, keys, 0);
            row.add(value != null ? value.toString() : "");
        }
        csv.addRow(row);
    }

    private static Object getNestedValue(Map<String, Object> map, String[] keys, int index) {
        if (index == keys.length - 1) {
            return map.get(keys[index]);
        } else {
            Object value = map.get(keys[index]);
            if (value instanceof Map) {
                return getNestedValue((Map<String, Object>) value, keys, index + 1);
            } else {
                return null;
            }
        }
    }
}
