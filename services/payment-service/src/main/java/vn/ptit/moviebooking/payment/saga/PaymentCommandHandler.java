package vn.ptit.moviebooking.payment.saga;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.ptit.moviebooking.common.Command;
import vn.ptit.moviebooking.payment.dto.request.PaymentRequest;
import vn.ptit.moviebooking.payment.entity.Payment;
import vn.ptit.moviebooking.payment.service.PaymentService;

@Component
public class PaymentCommandHandler {

    private static final Logger log = LoggerFactory.getLogger(PaymentCommandHandler.class);
    private final CommandGateway commandGateway;
    private final PaymentService paymentService; // service gọi API thật

    public PaymentCommandHandler(CommandGateway commandGateway, PaymentService paymentService) {
        this.commandGateway = commandGateway;
        this.paymentService = paymentService;
    }

    @CommandHandler
    public void handle(Command.ProcessPaymentCommand cmd) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setBookingId(1);
        paymentRequest.setAmount(cmd.getAmount());
        paymentRequest.setAtm("1212");
        paymentRequest.setPin("23342");
        Payment payment = paymentService.createPayment(paymentRequest);

        if (payment.getTransactionId() != null) {
            Command.ConfirmPaymentCommand confirmPaymentCommand = new Command.ConfirmPaymentCommand();
            BeanUtils.copyProperties(cmd, confirmPaymentCommand);
            commandGateway.send(confirmPaymentCommand);
        } else {
            Command.RefundCommand refundCommand = new Command.RefundCommand();
            BeanUtils.copyProperties(cmd, refundCommand);
            commandGateway.send(refundCommand);
        }
    }
}
