package vn.ptit.moviebooking.users.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import vn.ptit.moviebooking.users.config.SecurityProps;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Component
public class JwtProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtProvider.class);
    protected final SecretKey secretKey;

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
        secretKey = Keys.hmacShaKeyFor(keyBytes);
        log.debug("[GATEWAY_JWT_SIGNATURE_CONFIG] - Sign JWT with algorithm: {}", secretKey.getAlgorithm());
    }

    public String generateToken(int userId, String username) {
        long validityInMilliseconds = Instant.now().toEpochMilli() + 604800000;

        return Jwts.builder()
                .subject(username)
                .claim("userId", userId)
                .claim("username", username)
                .claim("authorities", "01,02")
                .signWith(secretKey)
                .issuedAt(new Date())
                .expiration(new Date(validityInMilliseconds))
                .compact();
    }
}
