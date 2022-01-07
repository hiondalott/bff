package com.ott.bff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("v1")
public class BFFController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${ottmember.host}")
    private String memberHost;
    @Value("${ottmember.port}")
    private String memberPort;
    @Value("${ottrecommend.host}")
    private String recommendHost;
    @Value("${ottrecommend.port}")
    private String recommendPort;

    @GetMapping("member/members/{id}")
    public Mono<String> getMember(@PathVariable String id, @RequestHeader HttpHeaders headers) {

        WebClient client = WebClient.create();

        headers.forEach((key, value) -> {
            log.info(">>>>> Header '{}' => {}", key, value);
        });

        String uri = "http://" + memberHost + ":" + memberPort + "/members/" + id;
        log.info("### API URI: {}", uri);

        return client.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);
    }

    @GetMapping("recommend/bestott/{id}")
    public Mono<String> getBestOTT(@PathVariable String id) {

        WebClient client = WebClient.create();

        String uri = "http://" + recommendHost + ":" + recommendPort + "/bestott/" + id;
        log.info("### API URI: {}", uri);

        return client.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);
    }
}
