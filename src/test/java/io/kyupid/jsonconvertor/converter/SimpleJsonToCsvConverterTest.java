package io.kyupid.jsonconvertor.converter;

import io.kyupid.jsonconvertor.exception.InvalidJsonException;
import io.kyupid.jsonconvertor.model.CSV;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimpleJsonToCsvConverterTest {

    Converter<CSV> converter = SimpleJsonToCsvConverter.getInstance();

    @Test
    @DisplayName("빈 JSON 배열을 입력으로 주었을 때, 빈 CSV가 반환된다")
    void convert_EmptyJsonArray_ReturnsEmptyCsv() {
        String jsonString = "[]";
        CSV csv = converter.convert(jsonString);
        assertThat(csv.getHeaders()).isEmpty();
        assertThat(csv.getRows()).isEmpty();
    }

    @Test
    @DisplayName("빈 JSON 객체를 입력으로 주었을 때, 빈 CSV가 반환된다")
    void convert_EmptyJsonObject_ReturnsEmptyCsv() {
        String jsonString = "{}";
        CSV csv = converter.convert(jsonString);
        assertThat(csv.getHeaders()).isEmpty();
        assertThat(csv.getRows()).isEmpty();
    }

    @Test
    @DisplayName("단일 JSON 객체를 입력으로 주었을 때, 헤더와 한 개의 행이 있는 CSV가 반환된다")
    void convert_SingleJsonObject_ReturnsCsvWithOneRow() {
        String jsonString = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";
        CSV csv = converter.convert(jsonString);
        assertThat(csv.getHeaders()).containsExactly("name", "age", "city");
        assertThat(csv.getRows()).containsExactly(List.of("John", "30", "New York"));
    }

    @Test
    @DisplayName("여러 JSON 객체가 포함된 JSON 배열을 입력으로 주었을 때, 헤더와 여러 행이 있는 CSV가 반환된다")
    void convert_JsonArrayWithMultipleObjects_ReturnsCsvWithMultipleRows() {
        String jsonString = "[{\"name\":\"John\",\"age\":30,\"city\":\"New York\"},{\"name\":\"Jane\",\"age\":25,\"city\":\"London\"}]";
        CSV csv = converter.convert(jsonString);
        assertThat(csv.getHeaders()).containsExactly("name", "age", "city");
        assertThat(csv.getRows()).containsExactly(
                List.of("John", "30", "New York"),
                List.of("Jane", "25", "London")
        );
    }

    @Test
    @DisplayName("일부 값이 누락된 JSON 객체를 입력으로 주었을 때, 해당 값이 빈 문자열로 대체되어 CSV에 포함된다")
    void convert_JsonObjectWithMissingValues_ReturnsCsvWithEmptyValues() {
        String jsonString = "{\"name\":\"John\",\"age\":30,\"city\":null}";
        CSV csv = converter.convert(jsonString);
        assertThat(csv.getHeaders()).containsExactly("name", "age", "city");
        assertThat(csv.getRows()).containsExactly(List.of("John", "30", ""));
    }

    @Test
    @DisplayName("중첩된 JSON 객체를 입력으로 주었을 때, 중첩된 객체의 키-값 쌍이 포함된 CSV가 생성된다")
    void convert_NestedJsonObject_IncludesNestedObjectsKeyValuePairsInCsv() {
        String jsonString = "{\"name\":\"John\",\"age\":30,\"address\":{\"city\":\"New York\",\"country\":\"USA\"}}";
        CSV csv = converter.convert(jsonString);
        assertThat(csv.getHeaders()).containsExactly("name", "age", "address.city", "address.country");
        assertThat(csv.getRows()).containsExactly(List.of("John", "30", "New York", "USA"));
    }

    @Test
    @DisplayName("JSON 배열 내부에 중첩된 JSON 객체가 포함된 경우, 중첩된 객체의 키-값 쌍이 CSV에 포함된다")
    void convert_JsonArrayWithNestedObjects_IncludesNestedObjectsKeyValuePairsInCsv() {
        String jsonString = """
                [
                  {
                    "name": "John",
                    "age": 30,
                    "address": {
                      "city": "New York",
                      "country": "USA"
                    }
                  },
                  {
                    "name": "Jane",
                    "age": 25,
                    "address": {
                      "city": "London",
                      "country": "UK"
                    }
                  }
                ]
                """;
        CSV csv = converter.convert(jsonString);
        assertThat(csv.getHeaders()).containsExactly("name", "age", "address.city", "address.country");
        assertThat(csv.getRows()).containsExactly(
                List.of("John", "30", "New York", "USA"),
                List.of("Jane", "25", "London", "UK")
        );
    }

    @Test
    @DisplayName("JSON 배열 내부에 서로 다른 키를 가진 JSON 객체들이 포함된 경우, 모든 고유한 키들이 CSV 헤더에 포함되고, 해당 키가 없는 객체의 값은 빈 문자열로 표시된다")
    void convert_JsonArrayWithDifferentKeys_IncludesAllUniqueKeysInHeaderAndEmptyValuesForMissingKeys() {
        String jsonString = "[{\"name\":\"John\",\"age\":30},{\"name\":\"Jane\",\"city\":\"London\"}]";
        CSV csv = converter.convert(jsonString);
        assertThat(csv.getHeaders()).containsExactly("name", "age", "city");
        assertThat(csv.getRows()).containsExactly(
                List.of("John", "30", ""),
                List.of("Jane", "", "London")
        );
    }

    @Test
    @DisplayName("값이 null인 프로퍼티가 포함된 JSON 객체를 입력으로 주었을 때, null 값이 빈 문자열로 대체되어 CSV에 포함된다")
    void convert_JsonObjectWithNullValues_ReturnsCsvWithEmptyValuesForNullProperties() {
        String jsonString = "{\"name\":\"John\",\"age\":null,\"city\":\"New York\"}";
        CSV csv = converter.convert(jsonString);
        assertThat(csv.getHeaders()).containsExactly("name", "age", "city");
        assertThat(csv.getRows()).containsExactly(List.of("John", "", "New York"));
    }

    @Test
    @DisplayName("빈 문자열 값을 가진 프로퍼티가 포함된 JSON 객체를 입력으로 주었을 때, 빈 문자열 값이 그대로 CSV에 포함된다")
    void convert_JsonObjectWithEmptyStringValues_ReturnsCsvWithEmptyStringValues() {
        String jsonString = "{\"name\":\"John\",\"age\":30,\"city\":\"\"}";
        CSV csv = converter.convert(jsonString);
        assertThat(csv.getHeaders()).containsExactly("name", "age", "city");
        assertThat(csv.getRows()).containsExactly(List.of("John", "30", ""));
    }

    @Test
    @DisplayName("특수 문자가 포함된 값을 가진 JSON 객체를 입력으로 주었을 때, 특수 문자가 이스케이프 처리되어 CSV에 포함된다")
    void convert_JsonObjectWithSpecialCharacters_EscapesSpecialCharactersInCsv() {
        String jsonString = "{\"name\":\"John, Doe\",\"description\":\"He said, \\\"Hello!\\\"\"}";
        CSV csv = converter.convert(jsonString);
        assertThat(csv.getHeaders()).containsExactly("name", "description");
        assertThat(csv.getRows()).containsExactly(List.of("\"John, Doe\"", "\"He said, \"\"Hello!\"\"\""));
    }

    @Test
    @Disabled // TODO
    @DisplayName("JSON 문자열 값 내부에 개행 문자가 포함된 경우, 개행 문자가 이스케이프 처리되어 CSV에 포함된다")
    void convert_JsonObjectWithNewlineInStringValue_EscapesNewlineInCsv() {
        String jsonString = "{\"name\":\"John\",\"description\":\"Line 1\\nLine 2\"}";
        CSV csv = converter.convert(jsonString);
        assertThat(csv.getHeaders()).containsExactly("name", "description");
        assertThat(csv.getRows()).containsExactly(List.of("John", "\"Line 1\\nLine 2\""));
    }

    @Test
    @DisplayName("잘못된 JSON 형식을 입력으로 주었을 때, InvalidJsonException 발생한다")
    void convert_InvalidJsonFormat_ThrowsIllegalArgumentException() {
        String jsonString = "invalid json";
        assertThatThrownBy(() -> converter.convert(jsonString))
                .isInstanceOf(InvalidJsonException.class)
                .hasMessage("Unexpected character: " + jsonString.charAt(0));
    }
}