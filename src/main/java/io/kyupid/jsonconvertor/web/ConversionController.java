package io.kyupid.jsonconvertor.web;

import io.kyupid.jsonconvertor.converter.Converter;
import io.kyupid.jsonconvertor.converter.ConverterFactory;
import io.kyupid.jsonconvertor.converter.ConverterType;
import io.kyupid.jsonconvertor.converter.SimpleJsonToCsvConverter;
import io.kyupid.jsonconvertor.model.CSV;
import io.kyupid.jsonconvertor.model.Excel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConversionController {
    private final ConverterFactory converterFactory;

    public ConversionController(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/convert")
    public String convert(@RequestParam("json") String json,
                          @RequestParam("converterType") ConverterType converterType,
                          Model model) {
        try {
            Converter<?> converter = converterFactory.getConverter(converterType);
            Object result = converter.convert(json);

            if (result instanceof CSV) {
                model.addAttribute("csv", result);
            } else if (result instanceof Excel) {
                model.addAttribute("excel", result);
            } else {
                throw new IllegalArgumentException("지원되지 않는 변환 타입입니다.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "JSON 변환 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "index";
    }
}