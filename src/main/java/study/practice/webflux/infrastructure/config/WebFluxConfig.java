package study.practice.webflux.infrastructure.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {

    @Bean
    public WebClient.Builder webClientBuilder() {

        return WebClient.builder()
            .clientConnector(
                new ReactorClientHttpConnector(
                    HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                        .doOnConnected(connection ->
                            connection.addHandlerLast(new ReadTimeoutHandler(10, TimeUnit.SECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(60, TimeUnit.SECONDS))
                        )
                )
            );
    }

    public Mono<String> callSomething() {

        return WebClient.create().get()
            .uri("/argoWorkflowUrl/buildName")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve() // Response에서 바로 Body를 가지고 온다. --> bodyToMono(String.class) 형태로 바로 사용 가능 (bodyToFlux도 사용 가능)
            .onStatus(httpStatus ->
                httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                response -> Mono.error(new RuntimeException("HttpStatus >>> " + response.statusCode()))
            )
            .bodyToMono(String.class);
    }
}
