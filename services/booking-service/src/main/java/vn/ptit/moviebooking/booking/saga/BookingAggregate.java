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

import java.util.ArrayList;
import java.util.Optional;

@Aggregate
public class BookingAggregate {

    @AggregateIdentifier
    private Integer bookingId;
    private final Logger logger = LoggerFactory.getLogger(BookingAggregate.class);

    protected BookingAggregate() {}

    @CommandHandler
    public BookingAggregate(Command.CreateBookingCommand createBookingCommand) {
        Event.CreateBookingEvent createBookingEvent = new Event.CreateBookingEvent();
        createBookingEvent.setBookingId(createBookingCommand.getBookingId());
        createBookingEvent.setTotalAmount(createBookingCommand.getTotalAmount());
        createBookingEvent.setSeatIds(Optional.ofNullable(createBookingCommand.getSeatIds()).orElse(new ArrayList<>()));
        AggregateLifecycle.apply(createBookingEvent);
        this.bookingId = createBookingEvent.getBookingId();
        System.out.println("Booking aggregate nhận command Tạo đơn hàng: "+ createBookingCommand.getBookingId());
    }

    @CommandHandler
    public void handle(Command.MarkBookingSuccessCommand successCommand) {
        Event.MarkBookingSuccessEvent bookingSuccessEvent = new Event.MarkBookingSuccessEvent();
        BeanUtils.copyProperties(successCommand, bookingSuccessEvent);
        AggregateLifecycle.apply(bookingSuccessEvent);
        System.out.println("Booking aggregate nhận command xác nhận Đặt hàng thành công");
    }

    @CommandHandler
    public void handle(Command.MarkBookingFailedCommand failedCommand) {
        Event.MarkBookingFailedEvent bookingFailedEvent = new Event.MarkBookingFailedEvent();
        bookingFailedEvent.setBookingId(failedCommand.getBookingId());
        bookingFailedEvent.setTransactionId(failedCommand.getTransactionId());
        bookingFailedEvent.setStatus(failedCommand.getStatus());
        bookingFailedEvent.setReason(failedCommand.getReason());
        bookingFailedEvent.setTotalAmount(failedCommand.getTotalAmount());
        bookingFailedEvent.setPaymentId(failedCommand.getPaymentId());
        AggregateLifecycle.apply(bookingFailedEvent);
        System.out.println("Booking aggregate nhận command báo Đặt hàng thất bại");
    }

    @EventSourcingHandler
    public void on(Event.CreateBookingEvent createBookingEvent) {
        this.bookingId = createBookingEvent.getBookingId();
    }
}
