package io.kyupid.jsonconvertor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CSV {
    private final List<String> headers;
    private final List<List<String>> rows = new ArrayList<>();

    public CSV(String... headers) {
        this.headers = Arrays.asList(headers);
    }

    public CSV(Set<String> headers) {
        this.headers = new ArrayList<>(headers);
    }

    public void addRow(String... data) {
        if (data.length != headers.size()) {
            throw new IllegalArgumentException("데이터의 개수가 헤더의 개수와 일치하지 않습니다.");
        }

        List<String> row = new ArrayList<>();
        for (String cell : data) {
            String escapedCell = escapeSpecialCharacters(cell);
            row.add(escapedCell);
        }
        rows.add(row);
    }

    private String escapeSpecialCharacters(String cell) {
        if (cell.contains(",") || cell.contains("\"") || cell.contains("\n")) {
            return "\"" + cell.replace("\"", "\"\"") + "\"";
        }
        return cell;
    }

    public boolean isEmpty() {
        return rows.isEmpty();
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<List<String>> getRows() {
        return rows;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join(",", headers)).append("\n");

        for (List<String> row : rows) {
            sb.append(String.join(",", row)).append("\n");
        }

        return sb.toString().trim();
    }
}