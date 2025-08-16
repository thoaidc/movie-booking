package vn.ptit.moviebooking.booking.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import vn.ptit.moviebooking.booking.dto.request.PaymentRequest;
import vn.ptit.moviebooking.booking.dto.response.BaseResponseDTO;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {

    @PostMapping("/api/p/payments")
    BaseResponseDTO createPaymentPending(PaymentRequest paymentRequest);
}
