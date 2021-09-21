package com.example.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@ResponseBody
@Controller
class ClientRestController {

	private final WebClient http;

	ClientRestController(WebClient http) {
		this.http = http;
	}

	@GetMapping("/call")
	Mono<String> call() {
		return this.http.get().uri("http://localhost:8080/hello/{w}", "Zipkin")
			.retrieve()
			.bodyToMono(String.class);
	}

}

@SpringBootApplication
public class ConsumerApplication {

	@Bean
	WebClient webClient(WebClient.Builder b) {
		return b.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

}
