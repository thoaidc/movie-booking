package vn.ptit.moviebooking.users.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashSet;

@SuppressWarnings("unused")
public class BaseUserDTO extends User {

    private final Integer id;

    private BaseUserDTO(Integer id,
                        String username,
                        String password,
                        boolean enabled,
                        boolean accountNonExpired,
                        boolean credentialsNonExpired,
                        boolean accountNonLocked,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static Builder userBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String username;
        private String password;
        private boolean enabled = true;
        private boolean accountNonExpired = true;
        private boolean credentialsNonExpired = true;
        private boolean accountNonLocked = true;
        private Collection<? extends GrantedAuthority> authorities = new HashSet<>();

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withAuthorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public BaseUserDTO build() {
            return new BaseUserDTO(
                this.id,
                this.username,
                this.password,
                this.enabled,
                this.accountNonExpired,
                this.credentialsNonExpired,
                this.accountNonLocked,
                this.authorities
            );
        }
    }
}
