package vn.ptit.moviebooking.booking.saga;

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
public class BookingAggregate {

    @AggregateIdentifier
    private String bookingId;
    private final Logger logger = LoggerFactory.getLogger(BookingAggregate.class);

    protected BookingAggregate() {}

    @CommandHandler
    public BookingAggregate(Command.CreateBookingCommand createBookingCommand) {
        Event.CreateBookingEvent createBookingEvent = new Event.CreateBookingEvent();
        BeanUtils.copyProperties(createBookingCommand, createBookingEvent);
        AggregateLifecycle.apply(createBookingEvent);
        logger.debug("Tạo đơn hàng command: {}", createBookingCommand.getBookingId());
    }

    @CommandHandler
    public void handle(Command.MarkBookingSuccessCommand successCommand) {
        Event.MarkBookingSuccessEvent bookingSuccessEvent = new Event.MarkBookingSuccessEvent();
        BeanUtils.copyProperties(successCommand, bookingSuccessEvent);
        AggregateLifecycle.apply(bookingSuccessEvent);
        logger.debug("Đặt hàng thành công command");
    }

    @CommandHandler
    public void handle(Command.MarkBookingFailedCommand failedCommand) {
        Event.MarkBookingFailedEvent bookingFailedEvent = new Event.MarkBookingFailedEvent();
        BeanUtils.copyProperties(failedCommand, bookingFailedEvent);
        AggregateLifecycle.apply(bookingFailedEvent);
        logger.debug("Đặt hàng thất bại command");
    }

    @EventSourcingHandler
    public void on(Event.CreateBookingEvent createBookingEvent) {
        logger.debug("Tạo đơn hàng event");
        this.bookingId = createBookingEvent.getBookingId();
    }

    @EventSourcingHandler
    public void on(Event.MarkBookingSuccessEvent bookingSuccessEvent) {
        logger.debug("Xác nhận đơn hàng event");
        this.bookingId = bookingSuccessEvent.getBookingId();
    }

    @EventSourcingHandler
    public void on(Event.MarkBookingFailedEvent bookingFailedEvent) {
        logger.debug("Hủy đơn hàng event");
        this.bookingId = bookingFailedEvent.getBookingId();
    }
}
