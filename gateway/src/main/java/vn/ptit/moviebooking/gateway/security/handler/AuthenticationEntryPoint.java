package vn.ptit.moviebooking.gateway.security.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationEntryPoint.class);

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException exception) {
        log.error("[AUTHENTICATION_ENTRY_POINT_ERROR] - At: {}", exception.getMessage());
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        String responseBody = "{\"code\":401,\"status\":false,\"message\":\"Xác thực thất bại\"}";
        DataBuffer buffer = response.bufferFactory().wrap(responseBody.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
