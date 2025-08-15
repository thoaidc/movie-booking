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
    private String paymentId;

    protected PaymentAggregate() {}

    @CommandHandler
    public PaymentAggregate(Command.ProcessPaymentCommand cmd) {
        boolean paid = true; // Gọi API thanh toán
        System.out.println("Thanh toán API");
        if (paid) {
            AggregateLifecycle.apply(new Event.PaymentCompletedEvent(cmd.getPaymentId()));
        } else {
            AggregateLifecycle.apply(new Event.PaymentFailedEvent(cmd.getPaymentId(), "Payment failed"));
        }
    }

    @EventSourcingHandler
    public void on(Event.PaymentCompletedEvent event) {
        this.paymentId = event.getPaymentId();
        System.out.println("Hoàn thành thanh toán");
    }

    @EventSourcingHandler
    public void on(Event.PaymentFailedEvent event) {
        System.out.println("Thanh toán toán thất bại");
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}
