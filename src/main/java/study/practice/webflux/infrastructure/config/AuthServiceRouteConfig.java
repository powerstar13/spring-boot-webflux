package study.practice.webflux.infrastructure.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.practice.webflux.infrastructure.filter.ReplaceMeToUserIdGatewayFilterFactory;
import study.practice.webflux.infrastructure.filter.TokenAuthGatewayFilterFactory;

@Configuration
@EnableConfigurationProperties
public class AuthServiceRouteConfig {

    // 이 어노테이션은 필드 또는 매개 변수에서 자동 배선할 때 후보 빈에 대한 한정자로 사용될 수 있습니다.
    @Qualifier("tokenAuthGatewayFilter")
    private TokenAuthGatewayFilterFactory tokenAuthGatewayFilterFactory;
    @Qualifier("replaceMeToUserIdGatewayFilter")
    private ReplaceMeToUserIdGatewayFilterFactory replaceMeToUserIdGatewayFilterFactory;

    @Value("${services.auth-service.url}")
    private String AUTH_SERVICE_URI;

    @Value("${services.domain}")
    private String SERVER_URL;

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("users-auths",
                predicateSpec -> predicateSpec.path("/auth/users/**")
                    .and().host(SERVER_URL)
                    .filters(gatewayFilterSpec -> gatewayFilterSpec
                        .filter(tokenAuthGatewayFilterFactory.apply(Object.class))
                        .filter(replaceMeToUserIdGatewayFilterFactory.apply(Object.class)))
                    .uri(AUTH_SERVICE_URI)
            ).build();
    }
}
