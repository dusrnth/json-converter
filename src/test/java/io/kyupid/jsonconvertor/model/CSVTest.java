package io.kyupid.jsonconvertor.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CSVTest {

    @Test
    @DisplayName("단일 행 추가 테스트")
    void addRow_ShouldAddSingleRow() {
        CSV csv = new CSV("Header1", "Header2", "Header3");
        csv.addRow("Data1", "Data2", "Data3");

        assertThat(csv.toString()).isEqualTo("Header1,Header2,Header3\nData1,Data2,Data3");
    }

    @Test
    @DisplayName("따옴표가 포함된 데이터 처리 테스트")
    void addRow_ShouldHandleQuotesInData() {
        CSV csv = new CSV("Header1", "Header2", "Header3");
        csv.addRow("Data1", "\"Data2\"", "Data3");

        assertThat(csv.toString()).isEqualTo("Header1,Header2,Header3\nData1,\"\"\"Data2\"\"\",Data3");
    }

    @Test
    @DisplayName("CSV 형식 문자열 변환 테스트")
    void toString_ShouldReturnCorrectCsvFormat() {
        CSV csv = new CSV("Header1", "Header2", "Header3");
        csv.addRow("Data1", "Data2", "Data3");
        csv.addRow("Data4", "Data5", "Data6");

        String expectedCsv = "Header1,Header2,Header3\nData1,Data2,Data3\nData4,Data5,Data6";
        assertThat(csv.toString()).isEqualTo(expectedCsv);
    }

    @Test
    @DisplayName("개행 문자가 포함된 데이터 처리 테스트")
    void addRow_ShouldHandleNewlineInData() {
        CSV csv = new CSV("Header1", "Header2", "Header3");
        csv.addRow("Data1", "Data\n2", "Data3");

        assertThat(csv.toString()).isEqualTo("Header1,Header2,Header3\nData1,\"Data\n2\",Data3");
    }

    @Test
    @DisplayName("쉼표가 포함된 데이터 처리 테스트")
    void addRow_ShouldHandleCommaInData() {
        CSV csv = new CSV("Header1", "Header2", "Header3");
        csv.addRow("Data1", "Data,2", "Data3");

        assertThat(csv.toString()).isEqualTo("Header1,Header2,Header3\nData1,\"Data,2\",Data3");
    }

    @Test
    @DisplayName("빈 문자열 데이터 처리 테스트")
    void addRow_ShouldHandleEmptyStringData() {
        CSV csv = new CSV("Header1", "Header2", "Header3");
        csv.addRow("", "", "");

        assertThat(csv.toString()).isEqualTo("Header1,Header2,Header3\n,,");
    }
}
