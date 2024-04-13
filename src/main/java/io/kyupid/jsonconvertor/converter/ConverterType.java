package io.kyupid.jsonconvertor.converter;

public enum ConverterType {
    ADVANCED_JSON_TO_CSV("advancedJsonToCsvConverter"),
    SIMPLE_JSON_TO_CSV("simpleJsonToCsvConverter"),
    ADVANCED_JSON_TO_EXCEL("advancedJsonToExcelConverter"),
    SIMPLE_JSON_TO_EXCEL("simpleJsonToExcelConverter");

    private final String converterName;

    ConverterType(String converterName) {
        this.converterName = converterName;
    }

    public String getConverterName() {
        return converterName;
    }
}