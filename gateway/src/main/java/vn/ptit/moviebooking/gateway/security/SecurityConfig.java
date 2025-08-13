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
import vn.ptit.moviebooking.gateway.configs.SecurityProps;

@Configuration
@EnableConfigurationProperties(SecurityProps.class)
@EnableWebFluxSecurity
public class SecurityConfig {

    private final ServerAuthenticationEntryPoint authenticationEntryPoint;
    private final ServerAccessDeniedHandler accessDeniedHandler;
    private final JwtFilter jwtFilter;

    public SecurityConfig(ServerAuthenticationEntryPoint authenticationEntryPoint,
                          ServerAccessDeniedHandler accessDeniedHandler,
                          JwtFilter jwtFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
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
                        exchanges.pathMatchers("/api/p/**").permitAll()
                                .anyExchange().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .build();
    }
}
