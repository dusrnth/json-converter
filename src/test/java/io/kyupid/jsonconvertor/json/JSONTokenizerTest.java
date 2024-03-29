package io.kyupid.jsonconvertor.json;

import io.kyupid.jsonconvertor.exception.InvalidJsonException;
import io.kyupid.jsonconvertor.json.token.Token;
import io.kyupid.jsonconvertor.json.token.TokenType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class JSONTokenizerTest {

    @Test
    @DisplayName("빈 객체 토큰화 테스트")
    void testTokenizeEmptyObject() {
        String json = "{}";
        JSONTokenizer tokenizer = new JSONTokenizer(json);
        List<Token> tokens = tokenizer.tokenize();
        assertThat(tokens).hasSize(2)
                .extracting(Token::getType)
                .containsExactly(TokenType.LEFT_BRACE, TokenType.RIGHT_BRACE);
    }

    @Test
    @DisplayName("간단한 객체 토큰화 테스트")
    void testTokenizeSimpleObject() {
        String json = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";
        JSONTokenizer tokenizer = new JSONTokenizer(json);
        List<Token> tokens = tokenizer.tokenize();
        assertThat(tokens).hasSize(9)
                .extracting(Token::getType)
                .containsExactly(
                        TokenType.LEFT_BRACE,
                        TokenType.STRING, TokenType.COLON, TokenType.STRING, TokenType.COMMA,
                        TokenType.STRING, TokenType.COLON, TokenType.NUMBER, TokenType.COMMA,
                        TokenType.STRING, TokenType.COLON, TokenType.STRING,
                        TokenType.RIGHT_BRACE
                );
    }

    @Test
    @DisplayName("빈 배열 토큰화 테스트")
    void testTokenizeEmptyArray() {
        String json = "[]";
        JSONTokenizer tokenizer = new JSONTokenizer(json);
        List<Token> tokens = tokenizer.tokenize();
        assertThat(tokens).hasSize(2)
                .extracting(Token::getType)
                .containsExactly(TokenType.LEFT_BRACKET, TokenType.RIGHT_BRACKET);
    }

    @Test
    @DisplayName("간단한 배열 토큰화 테스트")
    void testTokenizeSimpleArray() {
        String json = "[1,2,3,4,5]";
        JSONTokenizer tokenizer = new JSONTokenizer(json);
        List<Token> tokens = tokenizer.tokenize();
        assertThat(tokens).hasSize(11)
                .extracting(Token::getType)
                .containsExactly(
                        TokenType.LEFT_BRACKET,
                        TokenType.NUMBER, TokenType.COMMA,
                        TokenType.NUMBER, TokenType.COMMA,
                        TokenType.NUMBER, TokenType.COMMA,
                        TokenType.NUMBER, TokenType.COMMA,
                        TokenType.NUMBER,
                        TokenType.RIGHT_BRACKET
                );
    }

    @Test
    @DisplayName("문자열 토큰화 테스트")
    void testTokenizeString() {
        String json = "\"Hello, World!\"";
        JSONTokenizer tokenizer = new JSONTokenizer(json);
        List<Token> tokens = tokenizer.tokenize();
        assertThat(tokens).hasSize(1)
                .extracting(Token::getType)
                .containsExactly(TokenType.STRING);
        assertThat(tokens.get(0).getValue()).isEqualTo("Hello, World!");
    }

    @Test
    @DisplayName("숫자 토큰화 테스트")
    void testTokenizeNumber() {
        String json = "42";
        JSONTokenizer tokenizer = new JSONTokenizer(json);
        List<Token> tokens = tokenizer.tokenize();
        assertThat(tokens).hasSize(1)
                .extracting(Token::getType)
                .containsExactly(TokenType.NUMBER);
        assertThat(tokens.get(0).getValue()).isEqualTo("42");
    }

    @Test
    @DisplayName("불리언 토큰화 테스트")
    void testTokenizeBoolean() {
        String json = "true";
        JSONTokenizer tokenizer = new JSONTokenizer(json);
        List<Token> tokens = tokenizer.tokenize();
        assertThat(tokens).hasSize(1)
                .extracting(Token::getType)
                .containsExactly(TokenType.BOOLEAN);
        assertThat(tokens.get(0).getValue()).isEqualTo("true");
    }

    @Test
    @DisplayName("null 토큰화 테스트")
    void testTokenizeNull() {
        String json = "null";
        JSONTokenizer tokenizer = new JSONTokenizer(json);
        List<Token> tokens = tokenizer.tokenize();
        assertThat(tokens).hasSize(1)
                .extracting(Token::getType)
                .containsExactly(TokenType.NULL);
        assertThat(tokens.get(0).getValue()).isEqualTo("null");
    }

    @Test
    @DisplayName("유효하지 않은 JSON 문자열 토큰화 테스트")
    void testTokenizeInvalidJson() {
        String json = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"";
        JSONTokenizer tokenizer = new JSONTokenizer(json);
        assertThatThrownBy(tokenizer::tokenize)
                .isInstanceOf(InvalidJsonException.class);
    }

    @Test
    @DisplayName("유효하지 않은 숫자 토큰화 테스트")
    void testTokenizeInvalidNumber() {
        String json = "123abc";
        JSONTokenizer tokenizer = new JSONTokenizer(json);
        assertThatThrownBy(tokenizer::tokenize)
                .isInstanceOf(InvalidJsonException.class);
    }

    @Test
    @DisplayName("유효하지 않은 불리언 토큰화 테스트")
    void testTokenizeInvalidBoolean() {
        String json = "tru";
        JSONTokenizer tokenizer = new JSONTokenizer(json);
        assertThatThrownBy(tokenizer::tokenize)
                .isInstanceOf(InvalidJsonException.class);
    }

    @Test
    @DisplayName("유효하지 않은 null 토큰화 테스트")
    void testTokenizeInvalidNull() {
        String json = "nul";
        JSONTokenizer tokenizer = new JSONTokenizer(json);
        assertThatThrownBy(tokenizer::tokenize)
                .isInstanceOf(InvalidJsonException.class);
    }
}