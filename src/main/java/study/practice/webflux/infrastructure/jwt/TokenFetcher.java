package study.practice.webflux.infrastructure.jwt;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class TokenFetcher {

    public String getToken(ServerHttpRequest request) {
        return null;
    }
}
