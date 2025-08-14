package vn.ptit.moviebooking.booking.saga;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class BookingAggregate {

    @AggregateIdentifier
    private String bookingId;
    private String status;

    protected BookingAggregate() {}

    @CommandHandler
    public BookingAggregate(Command.CreateBookingCommand cmd) {
        System.out.println("Tạo đơn hàng");
        AggregateLifecycle.apply(new Event.BookingCreatedEvent(cmd.getBookingId(), cmd.getSeatIds(), cmd.getShowId(), cmd.getAmount()));
    }

    @EventSourcingHandler
    public void on(Event.BookingCreatedEvent event) {
        System.out.println("Tạo đơn hàng event");
        this.bookingId = event.getBookingId();
        this.status = "PENDING";
    }

    @CommandHandler
    public void handle(Command.MarkBookingConfirmedCommand cmd) {
        System.out.println("Xác nhận đơn hàng");
        AggregateLifecycle.apply(new Event.BookingConfirmedEvent(cmd.getBookingId()));
    }

    @CommandHandler
    public void handle(Command.MarkBookingFailedCommand cmd) {
        System.out.println("Xác nhận đơn hàng thất bại");
        AggregateLifecycle.apply(new Event.BookingFailedEvent(cmd.getBookingId(), cmd.getReason()));
    }

    @EventSourcingHandler
    public void on(Event.BookingConfirmedEvent event) {
        this.status = "CONFIRMED";
        System.out.println("Xác nhận đơn hàng event");
    }

    @EventSourcingHandler
    public void on(Event.BookingFailedEvent event) {
        this.status = "FAILED";
        System.out.println("Hủy đơn hàng event");
    }
}
