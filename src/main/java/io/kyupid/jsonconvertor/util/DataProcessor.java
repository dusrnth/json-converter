package io.kyupid.jsonconvertor.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kyupid.jsonconvertor.model.CsvData;
import io.kyupid.jsonconvertor.model.JsonData;

import java.io.FileWriter;
import java.io.IOException;

public class DataProcessor {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonData parseJsonData(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, JsonData.class);
    }

    public static void saveCsvData(CsvData csvData, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(csvData.toString());
        }
    }
}
