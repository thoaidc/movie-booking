package vn.ptit.moviebooking.payment.saga;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import vn.ptit.moviebooking.common.Command;
import vn.ptit.moviebooking.payment.dto.RefundRequest;
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
        boolean isPaymentSuccess = paymentService.paymentProcessTest(cmd.getPaymentId());
        Command.ProcessPaymentResultCommand processPaymentResultCommand = new Command.ProcessPaymentResultCommand();
        processPaymentResultCommand.setBookingId(cmd.getBookingId());
        processPaymentResultCommand.setTransactionId(cmd.getTransactionId());
        processPaymentResultCommand.setPaymentId(cmd.getPaymentId());
        processPaymentResultCommand.setAmount(cmd.getAmount());
        processPaymentResultCommand.setSuccess(isPaymentSuccess);
        processPaymentResultCommand.setSeatIds(cmd.getSeatIds());
        commandGateway.send(processPaymentResultCommand);
        System.out.println("Payment command handler xử lý thanh toán, kết quả: " + isPaymentSuccess);
    }

    @CommandHandler
    public void handle(Command.RefundCommand cmd) {
        RefundRequest request = new RefundRequest();
        request.setPaymentId(cmd.getPaymentId());
        request.setAmount(cmd.getAmount());
        request.setReason(cmd.getReason());
        paymentService.refund(request);
        Command.RefundResultCommand refundResultCommand = new Command.RefundResultCommand();
        refundResultCommand.setBookingId(cmd.getBookingId());
        refundResultCommand.setPaymentId(cmd.getPaymentId());
        refundResultCommand.setTransactionId(cmd.getTransactionId());
        refundResultCommand.setAmount(cmd.getAmount());
        refundResultCommand.setReason(cmd.getReason());
        refundResultCommand.setSeatIds(cmd.getSeatIds());
        commandGateway.send(refundResultCommand);
        System.out.println("Payment command handler xử lý yêu cầu hoàn tiền: " + cmd.getReason());
    }
}
