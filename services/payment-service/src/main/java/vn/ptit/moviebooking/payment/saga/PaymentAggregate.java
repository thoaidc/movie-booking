package vn.ptit.moviebooking.payment.saga;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;


@Aggregate
public class PaymentAggregate {

    @AggregateIdentifier
    private String bookingId;

    protected PaymentAggregate() {}

    @CommandHandler
    public PaymentAggregate(Command.ProcessPaymentCommand cmd) {
        boolean paid = true; // Gọi API thanh toán
        System.out.println("Thanh toán API");
        if (paid) {
            AggregateLifecycle.apply(new Event.PaymentCompletedEvent(cmd.getBookingId()));
        } else {
            AggregateLifecycle.apply(new Event.PaymentFailedEvent(cmd.getBookingId(), "Payment failed"));
        }
    }

    @EventSourcingHandler
    public void on(Event.PaymentCompletedEvent event) {
        this.bookingId = event.getBookingId();
        System.out.println("Hoàn thành thanh toán");
    }

    @EventSourcingHandler
    public void on(Event.PaymentFailedEvent event) {
        this.bookingId = event.getBookingId();
        System.out.println("Thanh toán toán thất bại");
    }
}
