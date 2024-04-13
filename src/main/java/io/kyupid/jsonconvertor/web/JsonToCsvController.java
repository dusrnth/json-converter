package io.kyupid.jsonconvertor.web;

import io.kyupid.jsonconvertor.converter.Converter;
import io.kyupid.jsonconvertor.converter.SimpleJsonToCsvConverter;
import io.kyupid.jsonconvertor.model.CSV;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JsonToCsvController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/convert")
    public String convert(@RequestParam("json") String json, Model model) {
        try {
            Converter<CSV> converter = SimpleJsonToCsvConverter.getInstance();
            CSV csv = converter.convert(json);
            model.addAttribute("csv", csv);
        } catch (Exception e) {
            model.addAttribute("error", "JSON 변환 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "index";
    }
}