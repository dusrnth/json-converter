package io.kyupid.jsonconvertor.converter;

import io.kyupid.jsonconvertor.json.JSON;
import io.kyupid.jsonconvertor.model.CSV;

import java.util.List;
import java.util.Map;

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

        Map<String, Object> firstItem = (Map<String, Object>) jsonArray.get(0);
        CSV csv = new CSV(firstItem.keySet().toArray(new String[0]));

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

        CSV csv = new CSV(jsonMap.keySet().toArray(new String[0]));
        addJsonMapToCsvRow(jsonMap, csv);
        return csv;
    }
    // JSON Map을 CSV의 행으로 추가하는 메서드
    private static void addJsonMapToCsvRow(Map<String, Object> jsonMap, CSV csv) {
        String[] values = new String[csv.getHeaders().size()];
        int index = 0;
        for (String key : csv.getHeaders()) {
            Object value = jsonMap.get(key);
            values[index++] = value != null ? value.toString() : "";
        }
        csv.addRow(values);
    }
}
