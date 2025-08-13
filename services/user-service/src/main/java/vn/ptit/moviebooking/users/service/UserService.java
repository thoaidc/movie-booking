package vn.ptit.moviebooking.users.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import vn.ptit.moviebooking.users.dto.request.LoginRequest;
import vn.ptit.moviebooking.users.dto.request.RegisterRequest;
import vn.ptit.moviebooking.users.dto.response.AuthenticationResponseDTO;
import vn.ptit.moviebooking.users.dto.response.BaseResponseDTO;
import vn.ptit.moviebooking.users.exception.BaseAuthenticationException;
import vn.ptit.moviebooking.users.exception.BaseBadRequestException;
import vn.ptit.moviebooking.users.entity.User;
import vn.ptit.moviebooking.users.repository.UserRepository;
import vn.ptit.moviebooking.users.security.JwtProvider;
import vn.ptit.moviebooking.users.security.UserDetailsCustom;

import java.util.Optional;

@Service
@Validated
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private static final String ENTITY_NAME = "UserService";
    private final AuthenticationManager authenticationManager;
    private final JwtProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       AuthenticationManager authenticationManager,
                       JwtProvider tokenProvider,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public User getById(Integer customerId) {
        Optional<User> customerOptional = userRepository.findById(customerId);

        if (customerOptional.isEmpty()) {
            throw new BaseBadRequestException(ENTITY_NAME, "Customer not exists");
        }

        return customerOptional.get();
    }

    @Transactional
    public BaseResponseDTO register(RegisterRequest request) {
        User user = new User();
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());

        if (userOptional.isPresent()) {
            throw new BaseBadRequestException("", "Tài khoản đã tồn tại");
        }

        BeanUtils.copyProperties(request, user);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return BaseResponseDTO.builder().message("Đăng ký thành công").ok(userRepository.save(user));
    }

    @Transactional
    public BaseResponseDTO login(LoginRequest request) {
        Authentication authentication = authenticate(request.getUsername(), request.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsCustom userDetails = (UserDetailsCustom) authentication.getPrincipal();
        User user = userDetails.getUser();
        AuthenticationResponseDTO results = new AuthenticationResponseDTO();
        BeanUtils.copyProperties(user, results);

        String jwtToken = tokenProvider.generateToken(user.getId(), request.getUsername());
        results.setToken(jwtToken);

        return BaseResponseDTO.builder()
                .code(200)
                .message("Đăng nhập thành công")
                .success(Boolean.TRUE)
                .result(results)
                .build();
    }

    private Authentication authenticate(String username, String rawPassword) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, rawPassword);

        try {
            return authenticationManager.authenticate(token);
        } catch (UsernameNotFoundException e) {
            log.error("[{}] - Account '{}' does not exists", e.getClass().getSimpleName(), username);
            throw new BaseBadRequestException(ENTITY_NAME, "Tài khoản không tồn tại");
        } catch (DisabledException e) {
            log.error("[{}] - Account disabled: {}", e.getClass().getSimpleName(), e.getMessage());
            throw new BaseBadRequestException(ENTITY_NAME, "Tài khoản đã bị dừng hoạt động");
        } catch (LockedException e) {
            log.error("[{}] - Account locked: {}", e.getClass().getSimpleName(), e.getMessage());
            throw new BaseBadRequestException(ENTITY_NAME, "Tài khoản đã bị khóa");
        } catch (AccountExpiredException e) {
            log.error("[{}] - Account expired: {}", e.getClass().getSimpleName(), e.getMessage());
            throw new BaseBadRequestException(ENTITY_NAME, "Tài khoản đã hết hạn");
        } catch (CredentialsExpiredException e) {
            log.error("[{}] - Credentials expired {}", e.getClass().getSimpleName(), e.getMessage());
            throw new BaseBadRequestException(ENTITY_NAME, "Thông tin xác thực không hợp lệ");
        } catch (BadCredentialsException e) {
            log.error("[{}] - Bad credentials {}", e.getClass().getSimpleName(), e.getMessage());
            throw new BaseBadRequestException(ENTITY_NAME, "Thông tin xác thực không hợp lệ");
        } catch (AuthenticationException e) {
            log.error("[{}] Authentication failed: {}", e.getClass().getSimpleName(), e.getMessage());
            throw new BaseAuthenticationException("Xác thực thất bại");
        }
    }
}
