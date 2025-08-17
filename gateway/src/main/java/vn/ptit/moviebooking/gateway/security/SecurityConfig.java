package vn.ptit.moviebooking.gateway.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import vn.ptit.moviebooking.gateway.configs.CorsProps;
import vn.ptit.moviebooking.gateway.configs.SecurityProps;

import java.time.Duration;
import java.util.Objects;

@Configuration
@EnableConfigurationProperties({SecurityProps.class, CorsProps.class})
@EnableWebFluxSecurity
public class SecurityConfig {

    private final ServerAuthenticationEntryPoint authenticationEntryPoint;
    private final ServerAccessDeniedHandler accessDeniedHandler;
    private final JwtFilter jwtFilter;
    private final CorsProps corsProps;

    public SecurityConfig(ServerAuthenticationEntryPoint authenticationEntryPoint,
                          ServerAccessDeniedHandler accessDeniedHandler,
                          JwtFilter jwtFilter, CorsProps corsProps) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.jwtFilter = jwtFilter;
        this.corsProps = corsProps;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .headers(headers -> headers
                        .frameOptions(Customizer.withDefaults())
                        .contentTypeOptions(Customizer.withDefaults())
                )
                .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange(exchanges ->
                        exchanges.pathMatchers("/api/p/**", "/actuator/**").permitAll()
                                .anyExchange().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        corsProps.getCorsConfigurations().forEach((path, config) -> {
            CorsConfiguration cors = new CorsConfiguration();
            cors.setAllowedOrigins(config.getAllowedOrigins());
            cors.setAllowedMethods(config.getAllowedMethods());
            cors.setAllowedHeaders(config.getAllowedHeaders());
            cors.setAllowCredentials(config.getAllowCredentials());

            if (Objects.nonNull(config.getMaxAge())) {
                cors.setMaxAge(Duration.ofSeconds(config.getMaxAge()));
            }

            source.registerCorsConfiguration(path, cors);
        });

        return source;
    }
}
