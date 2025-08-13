package vn.ptit.moviebooking.users.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "app.security")
@Configuration
public class SecurityProps {

    private JwtConfig jwt;

    public JwtConfig getJwt() {
        return jwt;
    }

    public void setJwt(JwtConfig jwt) {
        this.jwt = jwt;
    }

    public static class JwtConfig {

        private String base64SecretKey;
        private Long validity;
        private Long validityForRemember;

        public String getBase64SecretKey() {
            return base64SecretKey;
        }

        public void setBase64SecretKey(String base64SecretKey) {
            this.base64SecretKey = base64SecretKey;
        }

        public Long getValidity() {
            return validity;
        }

        public void setValidity(Long validity) {
            this.validity = validity;
        }

        public Long getValidityForRemember() {
            return validityForRemember;
        }

        public void setValidityForRemember(Long validityForRemember) {
            this.validityForRemember = validityForRemember;
        }
    }
}
