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

import java.util.HashSet;
import java.util.Set;

@Aggregate
public class MovieAggregate {

    @AggregateIdentifier
    private String seatReservationId;
    private final Logger logger = LoggerFactory.getLogger(MovieAggregate.class);
    private static final Set<Integer> seats = new HashSet<>();

    protected MovieAggregate() {}

    @CommandHandler
    public MovieAggregate(Command.ReserveSeatCommand cmd) {
        if (seats.containsAll(cmd.getSeatIds())) {
            Event.CancelSeatEvent cancelSeatEvent = new Event.CancelSeatEvent();
            BeanUtils.copyProperties(cmd, cancelSeatEvent);
            AggregateLifecycle.apply(cancelSeatEvent);
            logger.debug("Ghế đã bị giữ bởi người khác: {} - {}", cancelSeatEvent.getSeatReservationId(), cancelSeatEvent.getSeatIds());
        } else {
            Event.ReserveSeatEvent reserveSeatEvent = new Event.ReserveSeatEvent();
            BeanUtils.copyProperties(cmd, reserveSeatEvent);
            AggregateLifecycle.apply(reserveSeatEvent);
            logger.debug("Giữ ghế thành công: {} - {}", reserveSeatEvent.getSeatReservationId(), reserveSeatEvent.getSeatIds());
        }
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
        seats.addAll(event.getSeatIds());
        logger.debug("Giữ ghế event: {} - {}", event.getSeatReservationId(), event.getSeatIds());
    }

    @EventSourcingHandler
    public void on(Event.CancelSeatEvent event) {
        this.seatReservationId = event.getSeatReservationId();
        event.getSeatIds().forEach(seats::remove);
        logger.debug("Hủy giữ ghế event: {} - {}", event.getSeatReservationId(), event.getSeatIds());
    }
}
