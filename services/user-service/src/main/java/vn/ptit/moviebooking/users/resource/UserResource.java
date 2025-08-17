package vn.ptit.moviebooking.users.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.moviebooking.users.dto.response.BaseResponseDTO;
import vn.ptit.moviebooking.users.service.UserService;

@RestController
@RequestMapping("/api")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userId}")
    public BaseResponseDTO getUserById(@PathVariable Integer userId) {
        return BaseResponseDTO.builder().ok(userService.getById(userId));
    }

    @GetMapping("/p/users/{userId}")
    public BaseResponseDTO getUser(@PathVariable Integer userId) {
        return BaseResponseDTO.builder().ok(userService.findUserById(userId));
    }
}
