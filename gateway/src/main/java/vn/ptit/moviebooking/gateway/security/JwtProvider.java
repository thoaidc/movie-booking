package vn.ptit.moviebooking.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.SignatureException;

import jakarta.ws.rs.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import vn.ptit.moviebooking.gateway.configs.SecurityProps;
import vn.ptit.moviebooking.gateway.dto.BaseUserDTO;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtProvider.class);
    private final JwtParser jwtParser;

    public JwtProvider(SecurityProps securityProps) {
        SecurityProps.JwtConfig jwtConfig = Optional.ofNullable(securityProps).orElse(new SecurityProps()).getJwt();

        if (Objects.isNull(jwtConfig)) {
            log.warn("[JWT_CONFIG_NOT_FOUND] - JWT config is null! Fallback to default config");
        }

        String base64SecretKey = jwtConfig.getBase64SecretKey();

        if (!StringUtils.hasText(base64SecretKey)) {
            throw new IllegalArgumentException("Could not found secret key to sign JWT");
        }

        log.debug("[GATEWAY_JWT_SIGNATURE_CONFIG] - Using a Base64-encoded JWT secret key");
        byte[] keyBytes = Base64.getUrlDecoder().decode(base64SecretKey);
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parser().verifyWith(secretKey).build();
        log.debug("[GATEWAY_JWT_SIGNATURE_CONFIG] - Sign JWT with algorithm: {}", secretKey.getAlgorithm());
    }

    public Mono<Authentication> validateToken(String token) {
        return Mono.fromCallable(() -> parseToken(token))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Authentication parseToken(String token) {
        log.debug("[VALIDATE_TOKEN] - Validating token by default config");

        if (!StringUtils.hasText(token))
            throw new BadRequestException("Thông tin xác thực không hợp lệ");

        try {
            return getAuthentication(token);
        } catch (MalformedJwtException e) {
            log.error("[JWT_MALFORMED_ERROR] - Invalid JWT: {}", e.getMessage());
        } catch (SignatureException e) {
            log.error("[JWT_SIGNATURE_ERROR] - Invalid JWT signature: {}", e.getMessage());
        } catch (SecurityException e) {
            log.error("[JWT_SECURITY_ERROR] - Unable to decode JWT: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("[JWT_EXPIRED_ERROR] - Expired JWT: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("[ILLEGAL_ARGUMENT_ERROR] - Invalid JWT string (null, empty,...): {}", e.getMessage());
        }

        throw new RuntimeException("Token không hợp lệ");
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        log.debug("[RETRIEVE_AUTHENTICATION] - Claim authentication info from token after authenticated");
        Claims claims = (Claims) jwtParser.parse(token).getPayload();
        Integer userId = (Integer) claims.get("userId");
        String username = (String) claims.get("username");
        String authorities = (String) claims.get("authorities");

        Set<SimpleGrantedAuthority> userAuthorities = Arrays.stream(authorities.split(","))
                .filter(StringUtils::hasText)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        BaseUserDTO principal = BaseUserDTO.userBuilder()
                .withId(userId)
                .withUsername(username)
                .withPassword(username) // Not used but needed to avoid `argument 'content': null` error in spring security
                .withAuthorities(userAuthorities)
                .build();

        return new UsernamePasswordAuthenticationToken(principal, token, userAuthorities);
    }
}
