package vn.ptit.moviebooking.users.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.ptit.moviebooking.users.entity.User;
import vn.ptit.moviebooking.users.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        log.debug("Bean 'UserDetailsServiceImpl' is configured for load user credentials info");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Load user by username: {}", username);
        Optional<User> authentication = userRepository.findByUsername(username);

        if (authentication.isEmpty()) {
            throw new UsernameNotFoundException("Tài khoản không tồn tại");
        }

        return UserDetailsCustom.customBuilder().user(authentication.get()).authorities(Collections.emptySet()).build();
    }
}
