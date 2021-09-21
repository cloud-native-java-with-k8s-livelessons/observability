package com.example.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@ResponseBody
@SpringBootApplication
public class ClientApplication {

	@GetMapping("/hello/{name}")
	Map<String, String> hello(@PathVariable String name) {
		return Map.of("message", "Hello, " + name + "!");
	}

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

}
