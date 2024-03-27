package io.kyupid.jsonconvertor.web;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class JsonToCsvController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/convert")
    public void convertJsonToCsv(@RequestBody String json,
                                 @RequestParam(value = "format") String requestFormat,
                                 HttpServletResponse response) throws IOException {
        // 1. String json -> JSON json 파싱
        // 2. requestFormat 애 따라 Converter (어댑터) 설정
        // 3. result 반환
    }
}
