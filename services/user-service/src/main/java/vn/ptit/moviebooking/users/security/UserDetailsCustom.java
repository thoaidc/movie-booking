package vn.ptit.moviebooking.users.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsCustom extends User {

    private final vn.ptit.moviebooking.users.entity.User user;
    private final Set<String> authorities = new HashSet<>();

    private UserDetailsCustom(vn.ptit.moviebooking.users.entity.User user,
                              Collection<? extends GrantedAuthority> authorities,
                              boolean accountEnabled,
                              boolean accountNonExpired,
                              boolean credentialsNonExpired,
                              boolean accountNonLocked) {
        super(
            user.getUsername(),
            user.getPassword(),
            accountEnabled,
            accountNonExpired,
            credentialsNonExpired,
            accountNonLocked,
            authorities
        );

        this.user = user;
        this.authorities.addAll(authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
    }

    public vn.ptit.moviebooking.users.entity.User getUser() {
        return user;
    }

    public Set<String> getSetAuthorities() {
        return authorities;
    }

    public static Builder customBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private vn.ptit.moviebooking.users.entity.User user;
        private Collection<? extends GrantedAuthority> authorities;
        private boolean accountEnabled = true;
        private boolean accountNonExpired = true;
        private boolean accountNonLocked = true;

        public Builder user(vn.ptit.moviebooking.users.entity.User user) {
            this.user = user;
            return this;
        }

        public Builder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public UserDetailsCustom build() {
            boolean credentialsNonExpired = true;

            return new UserDetailsCustom(
                user,
                authorities,
                accountEnabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked
            );
        }
    }
}
