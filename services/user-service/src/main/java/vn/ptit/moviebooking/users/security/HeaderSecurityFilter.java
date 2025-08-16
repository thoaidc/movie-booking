package vn.ptit.moviebooking.users.security;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.ptit.moviebooking.users.common.JsonUtils;
import vn.ptit.moviebooking.users.dto.BaseUserDTO;
import vn.ptit.moviebooking.users.dto.response.BaseResponseDTO;
import vn.ptit.moviebooking.users.exception.BaseAuthenticationException;
import vn.ptit.moviebooking.users.exception.BaseException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

@Component
public class HeaderSecurityFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(HeaderSecurityFilter.class);
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {
        if (shouldAuthenticateRequest(request)) {
            try {
                authenticate(request);
            } catch (BaseException e) {
                handleAuthException(response, e);
                return;
            } catch (Exception e) {
                log.error("[AUTHENTICATION_FILTER_ERROR] - Unable to process exception: {}", e.getClass().getName(), e);
                throw e;
            }
        }

        filterChain.doFilter(request, response);
    }

    protected void handleAuthException(HttpServletResponse response, BaseException exception) throws IOException {
        log.error("[AUTHENTICATION_FILTER_ERROR] - {}", exception.getClass().getName(), exception);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        BaseResponseDTO responseDTO = BaseResponseDTO.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .success(Boolean.FALSE)
                .message(exception.getLocalizedMessage())
                .build();

        response.getWriter().write(JsonUtils.toJsonString(responseDTO));
        response.flushBuffer();
    }

    protected boolean shouldAuthenticateRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.info("[HEADER_SECURITY_FORWARD_FILTER] - Filtering {}: {}", request.getMethod(), requestURI);
        return Arrays.stream(new String[]{"/api/p/**", "/actuator/**"}).noneMatch(pattern -> antPathMatcher.match(pattern, requestURI));
    }

    protected void authenticate(HttpServletRequest request) {
        String userId = request.getHeader("userId");
        String username = request.getHeader("username");
        log.info("[RESOLVE_HEADER_FORWARDED] - userId: {}, username: {}", userId, username);

        BaseUserDTO userDTO = BaseUserDTO.userBuilder()
                .withId(Integer.parseInt(userId))
                .withUsername(username)
                .withPassword(username) // Not used but needed to avoid `argument 'content': null` error in spring security
                .withAuthorities(Collections.emptyList())
                .build();

        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDTO, username, userDTO.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.error("[AUTHENTICATE_HEADER_ERROR] - Could not set authentication from header forwarded: {}", e.getMessage());
            throw new BaseAuthenticationException("Không thể xác thực");
        }
    }
}
