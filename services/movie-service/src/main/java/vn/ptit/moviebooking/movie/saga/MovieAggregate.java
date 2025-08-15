package vn.ptit.moviebooking.movie.saga;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.ptit.moviebooking.common.Command;
import vn.ptit.moviebooking.common.Event;

import java.util.UUID;

@Aggregate
public class MovieAggregate {
    @AggregateIdentifier
    private String seatReservationId; // Đổi từ bookingId thành seatReservationId
//    private String bookingId; // Giữ bookingId như một thuộc tính bình thường

    private final Logger logger = LoggerFactory.getLogger(MovieAggregate.class);

    protected MovieAggregate() {}

    @CommandHandler
    public MovieAggregate(Command.ReserveSeatCommand cmd) {
        boolean available = true; // Check DB thực tế

        logger.debug("Giữ ghế thành công");
        System.out.println(": " + cmd.getSeatReservationId());

        if (available) {
            // Tạo unique ID cho seat reservation
            AggregateLifecycle.apply(new Event.SeatReservedEvent(
                    cmd.getSeatReservationId()
            ));
        } else {
            AggregateLifecycle.apply(new Event.SeatReservationFailedEvent(
                    cmd.getSeatReservationId(), "Seat not available"
            ));
        }
    }

    @EventSourcingHandler
    public void on(Event.SeatReservedEvent event) {
        this.seatReservationId = event.getSeatReservationId();
        logger.debug("Phát hành ghế event");
    }

    @CommandHandler
    public void handle(Command.CancelSeatCommand cmd) {
        // Update DB set ghế trống
        logger.debug("Hủy giữ ghế, ghế trống trở lại: " + cmd.getSeatIds());
    }

    public String getSeatReservationId() {
        return seatReservationId;
    }

    public void setSeatReservationId(String seatReservationId) {
        this.seatReservationId = seatReservationId;
    }
}
