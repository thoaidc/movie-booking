package vn.ptit.moviebooking.movie.saga;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import vn.ptit.moviebooking.common.Command;
import vn.ptit.moviebooking.common.Event;

@Aggregate
public class MovieAggregate {

    @AggregateIdentifier
    private String seatReservationId;

    protected MovieAggregate() {}

    @CommandHandler
    public MovieAggregate(Command.CreateCheckSeatCommand cmd) {
        Event.CreateCheckSeatEvent event = new Event.CreateCheckSeatEvent();
        event.setSeatReservationId(cmd.getSeatReservationId());
        event.setBookingId(cmd.getBookingId());
        event.setSeatIds(cmd.getSeatIds());
        AggregateLifecycle.apply(event);
        System.out.println("Movie aggregate nhận command khởi tạo check seat");
    }

    @CommandHandler
    public void handle(Command.ReserveSeatResultCommand cmd) {
        Event.ReserveSeatResultEvent event = new Event.ReserveSeatResultEvent();
        event.setBookingId(cmd.getBookingId());
        event.setSeatReservationId(cmd.getSeatReservationId());
        event.setSeatIds(cmd.getSeatIds());
        event.setSuccess(cmd.isSuccess());
        AggregateLifecycle.apply(event);
        System.out.println("Movie aggregate nhận command kết quả giữ ghế: " + cmd.isSuccess());
    }

    @CommandHandler
    public void handle(Command.CancelSeatResultCommand cmd) {
        Event.CancelSeatResultEvent event = new Event.CancelSeatResultEvent();
        event.setBookingId(cmd.getBookingId());
        event.setSeatReservationId(cmd.getSeatReservationId());
        event.setSeatIds(cmd.getSeatIds());
        AggregateLifecycle.apply(event);
        System.out.println("Movie aggreagte nhận command kết quả phát hành lại ghế");
    }

    @EventSourcingHandler
    public void on(Event.CreateCheckSeatEvent event) {
        this.seatReservationId = event.getSeatReservationId();
    }
}
