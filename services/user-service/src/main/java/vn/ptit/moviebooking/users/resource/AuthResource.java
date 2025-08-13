package vn.ptit.moviebooking.users.resource;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.moviebooking.users.dto.request.LoginRequest;
import vn.ptit.moviebooking.users.dto.request.RegisterRequest;
import vn.ptit.moviebooking.users.dto.response.BaseResponseDTO;
import vn.ptit.moviebooking.users.service.UserService;

@RestController
@RequestMapping("/api/p")
public class AuthResource {

    private final UserService userService;

    public AuthResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/register")
    public BaseResponseDTO register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/users/login")
    public BaseResponseDTO login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
