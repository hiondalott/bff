package com.ott.bff;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("members")
public class BFFController {

    @Value("${member.host}")
    private String memberHost;
    @Value("${member.port}")
    private String memberPort;

    @GetMapping("{id}")
    public Mono<String> getMember(@PathVariable String id) {
        WebClient client = WebClient.create();

        return client.get()
                .uri("http://" + memberHost + ":" + memberPort + "/members/" + id)
                .retrieve()
                .bodyToMono(String.class);
    }
}
