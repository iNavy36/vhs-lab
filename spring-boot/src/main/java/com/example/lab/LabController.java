package com.example.lab;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LabController {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}