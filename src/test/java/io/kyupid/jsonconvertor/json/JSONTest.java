package io.kyupid.jsonconvertor.json;

import io.kyupid.jsonconvertor.exception.InvalidJsonException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class JSONTest {

    @Test
    @DisplayName("빈 객체 파싱 테스트")
    void testParseEmptyObject() {
        String json = "{}";
        JSON jsonParser = new JSON(json);
        Object result = jsonParser.parse();
        assertThat(result).isInstanceOf(Map.class);
        assertThat((Map<?, ?>) result).isEmpty();
    }

    @Test
    @DisplayName("간단한 객체 파싱 테스트")
    void testParseSimpleObject() {
        String json = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";
        JSON jsonParser = new JSON(json);
        Object result = jsonParser.parse();
        assertThat(result).isInstanceOf(Map.class);
        Map<String, Object> map = (Map<String, Object>) result;
        assertThat(map).containsEntry("name", "John")
                .containsEntry("age", 30L)
                .containsEntry("city", "New York");
    }

    @Test
    @DisplayName("빈 배열 파싱 테스트")
    void testParseEmptyArray() {
        String json = "[]";
        JSON jsonParser = new JSON(json);
        Object result = jsonParser.parse();
        assertThat(result).isInstanceOf(List.class);
        assertThat((List<?>) result).isEmpty();
    }

    @Test
    @DisplayName("간단한 배열 파싱 테스트")
    void testParseSimpleArray() {
        String json = "[1,2,3,4,5]";
        JSON jsonParser = new JSON(json);
        Object result = jsonParser.parse();
        assertThat(result).isInstanceOf(List.class);
        List<Object> list = (List<Object>) result;
        assertThat(list).containsExactly(1L, 2L, 3L, 4L, 5L);
    }

    @Test
    @DisplayName("중첩된 객체 파싱 테스트")
    void testParseNestedObject() {
        String json = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\",\"hobbies\":[\"reading\",\"gaming\"],\"married\":false,\"spouse\":null}";
        JSON jsonParser = new JSON(json);
        Object result = jsonParser.parse();
        assertThat(result).isInstanceOf(Map.class);
        Map<String, Object> map = (Map<String, Object>) result;
        assertThat(map).containsEntry("name", "John")
                .containsEntry("age", 30L)
                .containsEntry("city", "New York")
                .containsKey("hobbies")
                .containsEntry("married", false)
                .containsEntry("spouse", null);
        assertThat(map.get("hobbies")).isInstanceOf(List.class);
        List<Object> hobbies = (List<Object>) map.get("hobbies");
        assertThat(hobbies).containsExactly("reading", "gaming");
    }

    @Test
    @DisplayName("중첩된 배열 파싱 테스트")
    void testParseNestedArray() {
        String json = "[[1,2],[3,4],[5,6]]";
        JSON jsonParser = new JSON(json);
        Object result = jsonParser.parse();
        assertThat(result).isInstanceOf(List.class);
        List<Object> list = (List<Object>) result;
        assertThat(list).hasSize(3);
        assertThat(list.get(0)).isInstanceOf(List.class)
                .asList().containsExactly(1L, 2L);
        assertThat(list.get(1)).isInstanceOf(List.class)
                .asList().containsExactly(3L, 4L);
        assertThat(list.get(2)).isInstanceOf(List.class)
                .asList().containsExactly(5L, 6L);
    }

    @Test
    @DisplayName("문자열 파싱 테스트")
    void testParseString() {
        String json = "\"Hello, World!\"";
        JSON jsonParser = new JSON(json);
        Object result = jsonParser.parse();
        assertThat(result).isInstanceOf(String.class)
                .isEqualTo("Hello, World!");
    }

    @Test
    @DisplayName("숫자 파싱 테스트")
    void testParseNumber() {
        String json = "42";
        JSON jsonParser = new JSON(json);
        Object result = jsonParser.parse();
        assertThat(result).isInstanceOf(Long.class)
                .isEqualTo(42L);
    }

    @Test
    @DisplayName("불리언 파싱 테스트")
    void testParseBoolean() {
        String json = "true";
        JSON jsonParser = new JSON(json);
        Object result = jsonParser.parse();
        assertThat(result).isInstanceOf(Boolean.class)
                .isEqualTo(true);
    }

    @Test
    @DisplayName("null 파싱 테스트")
    void testParseNull() {
        String json = "null";
        JSON jsonParser = new JSON(json);
        Object result = jsonParser.parse();
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("유효하지 않은 JSON 문자열 파싱 테스트")
    void testParseInvalidJson() {
        String json = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"";
        assertThatThrownBy(() -> new JSON(json))
                .isInstanceOf(InvalidJsonException.class);
    }

    @Test
    @DisplayName("유효하지 않은 숫자 파싱 테스트")
    void testParseInvalidNumber() {
        String json = "123abc";
        assertThatThrownBy(() -> new JSON(json))
                .isInstanceOf(InvalidJsonException.class);
    }

    @Test
    @DisplayName("유효하지 않은 불리언 파싱 테스트")
    void testParseInvalidBoolean() {
        String json = "tru";
        assertThatThrownBy(() -> new JSON(json))
                .isInstanceOf(InvalidJsonException.class);
    }

    @Test
    @DisplayName("유효하지 않은 null 파싱 테스트")
    void testParseInvalidNull() {
        String json = "nul";
        assertThatThrownBy(() -> new JSON(json))
                .isInstanceOf(InvalidJsonException.class);
    }
}