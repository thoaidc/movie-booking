package vn.ptit.moviebooking.movie.saga;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import vn.ptit.moviebooking.common.Command;
import vn.ptit.moviebooking.common.Event;

@Aggregate
public class MovieAggregate {

    @AggregateIdentifier
    private String seatReservationId;
    private final Logger logger = LoggerFactory.getLogger(MovieAggregate.class);

    protected MovieAggregate() {}

    @CommandHandler
    public MovieAggregate(Command.ReserveSeatCommand cmd) {
        Event.ReserveSeatEvent reserveSeatEvent = new Event.ReserveSeatEvent();
        BeanUtils.copyProperties(cmd, reserveSeatEvent);
        AggregateLifecycle.apply(reserveSeatEvent);
        logger.debug("Giữ ghế thành công: {} - {}", reserveSeatEvent.getSeatReservationId(), reserveSeatEvent.getSeatIds());
    }

    @CommandHandler
    public void handle(Command.CancelSeatCommand cmd) {
        Event.CancelSeatEvent cancelSeatEvent = new Event.CancelSeatEvent();
        BeanUtils.copyProperties(cmd, cancelSeatEvent);
        AggregateLifecycle.apply(cancelSeatEvent);
        logger.debug("Hủy giữ ghế: {} - {}", cancelSeatEvent.getSeatReservationId(), cancelSeatEvent.getSeatIds());
    }

    @EventSourcingHandler
    public void on(Event.ReserveSeatEvent event) {
        this.seatReservationId = event.getSeatReservationId();
        logger.debug("Giữ ghế event: {} - {}", event.getSeatReservationId(), event.getSeatIds());
        // Update DB
    }

    @EventSourcingHandler
    public void on(Event.CancelSeatEvent event) {
        this.seatReservationId = event.getSeatReservationId();
        logger.debug("Hủy giữ ghế event: {} - {}", event.getSeatReservationId(), event.getSeatIds());
        // Update DB
    }
}
