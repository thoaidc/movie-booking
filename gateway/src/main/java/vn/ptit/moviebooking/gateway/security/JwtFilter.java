package vn.ptit.moviebooking.gateway.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class JwtFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtProvider jwtProvider;
    PathPatternParser parser = new PathPatternParser();

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    @NonNull
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        String requestUri = exchange.getRequest().getURI().getPath();
        log.debug("[GATEWAY_JWT_FILTER] - Filtering request: {}: {}", exchange.getRequest().getMethod(), requestUri);

        if (parser.parse("/api/p/**").matches(PathContainer.parsePath(requestUri))) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            authHeader = authHeader.substring(7);
        }

        return jwtProvider.validateToken(authHeader)
                .flatMap(authentication -> setAuthentication(exchange, chain, authentication))
                .onErrorResume(error -> handleUnauthorized(exchange, error));
    }

    private Mono<Void> setAuthentication(ServerWebExchange exchange, WebFilterChain chain, Authentication auth) {
        exchange.getAttributes().put("authentication", auth);
        return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
    }

    private Mono<Void> handleUnauthorized(ServerWebExchange exchange, Throwable e) {
        log.error("[GATEWAY_JWT_VALIDATE_ERROR] - Token validation failed: {}", e.getMessage(), e);
        ServerHttpResponse response = exchange.getResponse();
        String responseBody = "{\"code\":401,\"status\":false,\"message\":\"Không có quyền truy cập\"}";
        DataBuffer buffer = response.bufferFactory().wrap(responseBody.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
