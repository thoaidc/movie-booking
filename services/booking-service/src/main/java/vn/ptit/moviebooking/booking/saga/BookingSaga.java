package vn.ptit.moviebooking.booking.saga;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.ptit.moviebooking.common.Command;
import vn.ptit.moviebooking.common.Event;

import java.util.UUID;

@Saga
@ProcessingGroup("booking-saga")
public class BookingSaga {

    @Autowired
    private transient CommandGateway commandGateway;
    private final Logger logger = LoggerFactory.getLogger(BookingSaga.class);

    public BookingSaga() {}

    @StartSaga
    @SagaEventHandler(associationProperty = "bookingId")
    public void on(Event.CreateBookingEvent event) {
        String seatReservationId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("seatReservationId", seatReservationId);
        Command.ReserveSeatCommand reserveSeatCommand = new Command.ReserveSeatCommand();
        reserveSeatCommand.setBookingId(event.getBookingId());
        reserveSeatCommand.setSeatReservationId(seatReservationId);
        reserveSeatCommand.setSeatIds(event.getSeatIds());
        commandGateway.send(reserveSeatCommand);
        logger.debug("Handle create booking saga with bookingId: {} - reserveSeatCommandId: {}", event.getBookingId(), reserveSeatCommand);
    }

    @SagaEventHandler(associationProperty = "seatReservationId")
    public void handle(Event.ReserveSeatEvent event) {
        String paymentId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("paymentId", paymentId);
        Command.CreatePaymentCommand createPaymentCommand = new Command.CreatePaymentCommand();
        createPaymentCommand.setBookingId(event.getBookingId());
        createPaymentCommand.setPaymentId(paymentId);
        createPaymentCommand.setAmount(event.getAmount());
        commandGateway.send(createPaymentCommand);
        logger.debug("Handle reserve seats success: {}, send payment command: {}", event.getSeatReservationId(), paymentId);
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void handle(Event.CreatePaymentEvent event) {
        Command.ProcessPaymentCommand processPaymentCommand = new Command.ProcessPaymentCommand();
        processPaymentCommand.setBookingId(event.getBookingId());
        processPaymentCommand.setPaymentId(event.getPaymentId());
        processPaymentCommand.setAmount(event.getAmount());
        commandGateway.send(processPaymentCommand);
        logger.debug("Handle created payment pending, send payment command: {}", event.getPaymentId());
    }

    @SagaEventHandler(associationProperty = "seatReservationId")
    public void handle(Event.CancelSeatEvent event) {
        Command.MarkBookingFailedCommand bookingFailedCommand = new Command.MarkBookingFailedCommand();
        bookingFailedCommand.setBookingId(event.getBookingId());
        bookingFailedCommand.setReason("Giữ ghế thất bại");
        commandGateway.send(bookingFailedCommand);
        logger.debug("Handle reserve seats failed: {}, send cancel order command: {}", event.getSeatReservationId(), event.getBookingId());
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void handle(Event.ConfirmPaymentEvent event) {
        Command.MarkBookingSuccessCommand bookingSuccessCommand = new Command.MarkBookingSuccessCommand();
        bookingSuccessCommand.setBookingId(event.getBookingId());
        commandGateway.send(bookingSuccessCommand);
        logger.debug("Handle payment success event: {}, send success booking command: {}", event.getPaymentId(), event.getBookingId());
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void handle(Event.RefundEvent event) {
        Command.CancelSeatCommand cancelSeatCommand = new Command.CancelSeatCommand();
        cancelSeatCommand.setBookingId(event.getBookingId());
        Command.MarkBookingFailedCommand bookingFailedCommand = new Command.MarkBookingFailedCommand();
        bookingFailedCommand.setBookingId(event.getBookingId());
        bookingFailedCommand.setReason("Thanh toán thất bại");
        commandGateway.send(cancelSeatCommand);
        commandGateway.send(bookingFailedCommand);
        logger.debug("Handle payment failed: {}, send cancel order command: {}", event.getPaymentId(), event.getBookingId());
    }

    @SagaEventHandler(associationProperty = "bookingId")
    @EndSaga
    public void handle(Event.MarkBookingSuccessEvent event) {
        System.out.println("Booking successfully! End Saga.");
    }

    @SagaEventHandler(associationProperty = "bookingId")
    @EndSaga
    public void handle(Event.MarkBookingFailedEvent event) {
        System.out.println("Booking failed! End Saga.");
    }
}
