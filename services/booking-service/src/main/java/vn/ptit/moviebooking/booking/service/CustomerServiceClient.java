package vn.ptit.moviebooking.booking.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import vn.ptit.moviebooking.booking.dto.response.BaseResponseDTO;

@FeignClient(name = "customer-service")
public interface CustomerServiceClient {

    @GetMapping("/api/customers/{customerId}")
    BaseResponseDTO getCustomerInfo(@PathVariable Integer customerId);
}
