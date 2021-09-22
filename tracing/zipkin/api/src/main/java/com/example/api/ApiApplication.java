package com.example.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@SpringBootApplication
public class ApiApplication {

    @GetMapping("/hello")
    String hello() {
        return "hello, world!";
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
