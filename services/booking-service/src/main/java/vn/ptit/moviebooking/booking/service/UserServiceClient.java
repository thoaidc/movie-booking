package vn.ptit.moviebooking.booking.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import vn.ptit.moviebooking.booking.dto.response.BaseResponseDTO;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/users/{userId}")
    BaseResponseDTO getUserInfo(@PathVariable Integer userId);
}
