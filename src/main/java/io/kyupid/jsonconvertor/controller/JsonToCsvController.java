package io.kyupid.jsonconvertor.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
public class JsonToCsvController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/convert")
    public void convertJsonToCsv(@RequestBody String json, HttpServletResponse response) throws IOException {
        return;
    }
}
