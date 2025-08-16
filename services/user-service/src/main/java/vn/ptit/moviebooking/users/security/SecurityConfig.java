package vn.ptit.moviebooking.users.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import vn.ptit.moviebooking.users.common.SecurityUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    private final HeaderSecurityFilter headerSecurityFilter;

    protected SecurityConfig(HeaderSecurityFilter headerSecurityFilter) {
        this.headerSecurityFilter = headerSecurityFilter;
    }

    @Bean
    @ConditionalOnMissingBean(MvcRequestMatcher.Builder.class)
    public MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        log.debug("[MCV_REQUEST_MATCHER_AUTO_CONFIG] - Use default mvc request matcher builder");
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        log.debug("[PASSWORD_ENCODER_AUTO_CONFIG] - Use default password encoder with encrypt factor: 12");
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnBean(UserDetailsService.class)
    @ConditionalOnMissingBean(DaoAuthenticationProvider.class)
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                            PasswordEncoder passwordEncoder) {
        log.debug("[DAO_AUTHENTICATION_PROVIDER_AUTO_CONFIG] - Use default authentication provider");
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    /**
     * This is a bean that provides an AuthenticationManager from Spring Security, used to authenticate users
     */
    @Bean
    @ConditionalOnMissingBean(AuthenticationManager.class)
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        log.debug("[AUTHENTICATION_MANAGER_AUTO_CONFIG] - Use default authentication manager");
        return auth.getAuthenticationManager();
    }

    @Bean
    @Primary
    public SecurityFilterChain securityFilterChain(MvcRequestMatcher.Builder mvc,
                                                   HttpSecurity http) throws Exception {
        log.debug("[SECURITY_FILTER_CHAIN_AUTO_CONFIG] - Using bean: `securityFilterChain`");
        http.csrf(AbstractHttpConfigurer::disable).cors(Customizer.withDefaults());
        http.addFilterBefore(headerSecurityFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers(header -> header
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                .referrerPolicy(config ->
                        config.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
        );

        http.sessionManagement(sessionManager ->
                sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(registry -> registry
                        .requestMatchers(SecurityUtils.convertToMvcMatchers(mvc, new String[]{ "/api/p/**", "/actuator/**"}))
                        .permitAll()
                        // Used with custom CORS filters in CORS (Cross-Origin Resource Sharing) mechanism
                        // The browser will send OPTIONS requests (preflight requests) to check
                        // if the server allows access from other sources before send requests such as POST, GET
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
