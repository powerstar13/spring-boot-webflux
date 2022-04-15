package study.practice.webflux.infrastructure.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import study.practice.webflux.infrastructure.jwt.JwtParseUtil;
import study.practice.webflux.infrastructure.jwt.TokenFetcher;

@Component
@RequiredArgsConstructor
public class TokenAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final JwtParseUtil jwtParseUtil;
    private final TokenFetcher tokenFetcher;

    public Boolean isTokenExist(ServerHttpRequest request) {
        return null;
    }

    public Boolean isTokenExpired(ServerHttpRequest request) {
        return null;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return null;
    }
}
