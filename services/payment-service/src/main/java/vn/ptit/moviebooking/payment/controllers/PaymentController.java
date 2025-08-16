package vn.ptit.moviebooking.payment.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.moviebooking.payment.dto.request.PaymentRequest;
import vn.ptit.moviebooking.payment.dto.response.BaseResponseDTO;
import vn.ptit.moviebooking.payment.service.PaymentService;

@RestController
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/p/payments")
    public BaseResponseDTO createPayment(@RequestBody PaymentRequest request) {
        return paymentService.createPaymentPending(request);
    }
}
