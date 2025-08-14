package vn.ptit.moviebooking.movie.saga;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class MovieAggregate {

    @AggregateIdentifier
    private String bookingId;

    protected MovieAggregate() {}

    @CommandHandler
    public MovieAggregate(Command.ReserveSeatCommand cmd) {
        boolean available = true; // Check DB thực tế

        System.out.println("Giữ ghế thành công");

        if (available) {
            AggregateLifecycle.apply(new Event.SeatReservedEvent(cmd.getBookingId()));
        } else {
            AggregateLifecycle.apply(new Event.SeatReservationFailedEvent(cmd.getBookingId(), "Seat not available"));
        }
    }

    @EventSourcingHandler
    public void on(Event.SeatReservedEvent event) {
        this.bookingId = event.getBookingId();

        System.out.println("Phát hành ghế event");
    }

    @CommandHandler
    public void handle(Command.CancelSeatCommand cmd) {
        // Update DB set ghế trống
        System.out.println("Hủy giữ ghế, ghế trống trở lại: " + cmd.getSeatIds());
    }
}
