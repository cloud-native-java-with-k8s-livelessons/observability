package com.example.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ClientApplication {

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }


    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

}


@Controller
@ResponseBody
class ClientRestController {

    private final WebClient http;

    ClientRestController(WebClient http) {
        this.http = http;
    }

    @GetMapping("/client")
    Mono<String> callApi() {
        return this.http.get().uri("http://localhost:8081/hello")
                .retrieve()
                .bodyToMono(String.class);
    }
}