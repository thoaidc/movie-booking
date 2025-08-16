package vn.ptit.moviebooking.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.moviebooking.payment.constants.PaymentConstants;
import vn.ptit.moviebooking.payment.dto.RefundRequest;
import vn.ptit.moviebooking.payment.dto.request.PaymentRequest;
import vn.ptit.moviebooking.payment.dto.response.BaseResponseDTO;
import vn.ptit.moviebooking.payment.entity.Payment;
import vn.ptit.moviebooking.payment.entity.Refund;
import vn.ptit.moviebooking.payment.repository.PaymentRepository;
import vn.ptit.moviebooking.payment.repository.RefundRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RefundRepository refundRepository;

    public PaymentService(PaymentRepository paymentRepository, RefundRepository refundRepository) {
        this.paymentRepository = paymentRepository;
        this.refundRepository = refundRepository;
    }

    @Transactional
    public BaseResponseDTO createPaymentPending(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setBookingId(paymentRequest.getBookingId());
        payment.setStatus(PaymentConstants.PaymentStatus.PENDING);
        paymentRepository.save(payment);
        return BaseResponseDTO.builder().ok(payment);
    }

    @Transactional
    public boolean paymentProcessTest(Integer paymentId) {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);

        if (paymentOptional.isEmpty()) {
            return false;
        }

        Payment payment = paymentOptional.get();
        boolean result = ThreadLocalRandom.current().nextBoolean();

        if (result) {
            payment.setStatus(PaymentConstants.PaymentStatus.COMPLETED);
            paymentRepository.save(payment);
            return true;
        } else {
            payment.setStatus(PaymentConstants.PaymentStatus.FAILED);
            paymentRepository.save(payment);
            return false;
        }
    }

    @Transactional
    public Refund refund(RefundRequest refundRequest) {
        Refund refund = new Refund();
        refund.setPaymentId(refundRequest.getPaymentId());
        refund.setAmount(refundRequest.getAmount());
        refund.setReason(refundRequest.getReason());
        refund.setRefundTime(ZonedDateTime.now(ZoneId.systemDefault()));
        return refundRepository.save(refund);
    }
}
