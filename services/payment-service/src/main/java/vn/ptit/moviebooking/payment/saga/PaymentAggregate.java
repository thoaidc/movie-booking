package vn.ptit.moviebooking.payment.saga;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import vn.ptit.moviebooking.common.Command;
import vn.ptit.moviebooking.common.Event;

@Aggregate
public class PaymentAggregate {

    @AggregateIdentifier
    private Integer paymentId;

    public PaymentAggregate() {}

    @CommandHandler
    public PaymentAggregate(Command.CreatePaymentCommand cmd) {
        Event.CreatePaymentEvent createPaymentEvent = new Event.CreatePaymentEvent();
        createPaymentEvent.setBookingId(cmd.getBookingId());
        createPaymentEvent.setPaymentId(cmd.getPaymentId());
        createPaymentEvent.setAmount(cmd.getAmount());
        createPaymentEvent.setSeatIds(cmd.getSeatIds());
        AggregateLifecycle.apply(createPaymentEvent);
        System.out.println("Payment aggregate nhận command khởi tạo: " + cmd.getPaymentId());
    }

    @CommandHandler
    public void handle(Command.ProcessPaymentResultCommand cmd) {
        Event.PaymentResultEvent paymentResultEvent = new Event.PaymentResultEvent();
        paymentResultEvent.setPaymentId(cmd.getPaymentId());
        paymentResultEvent.setBookingId(cmd.getBookingId());
        paymentResultEvent.setAmount(cmd.getAmount());
        paymentResultEvent.setSuccess(cmd.isSuccess());
        paymentResultEvent.setSeatIds(cmd.getSeatIds());
        AggregateLifecycle.apply(paymentResultEvent);
        System.out.println("Payment aggregate nhận command kết quả thanh toán: " + cmd.isSuccess());
    }

    @CommandHandler
    public void handle(Command.RefundResultCommand cmd) {
        Event.RefundResultEvent refundResultEvent = new Event.RefundResultEvent();
        refundResultEvent.setPaymentId(cmd.getPaymentId());
        refundResultEvent.setBookingId(cmd.getBookingId());
        refundResultEvent.setAmount(cmd.getAmount());
        refundResultEvent.setReason(cmd.getReason());
        refundResultEvent.setSeatIds(cmd.getSeatIds());
        AggregateLifecycle.apply(refundResultEvent);
        System.out.println("Payment aggregate nhận command kết quả hoàn tiền: " + cmd.getReason());
    }

    @EventSourcingHandler
    public void on(Event.CreatePaymentEvent event) {
        this.paymentId = event.getPaymentId();
    }
}
