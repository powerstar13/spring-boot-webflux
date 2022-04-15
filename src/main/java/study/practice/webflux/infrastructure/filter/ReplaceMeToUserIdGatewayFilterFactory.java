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
public class ReplaceMeToUserIdGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final JwtParseUtil jwtParseUtil;
    private final TokenFetcher tokenFetcher;

    public Integer parseUserId(ServerHttpRequest request) {
        return tokenFetcher.getToken(request) != null ? jwtParseUtil.parseUserId(tokenFetcher.getToken(request)) : null;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return null;
    }
}
