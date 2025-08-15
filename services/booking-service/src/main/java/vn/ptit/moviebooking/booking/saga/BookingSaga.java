package vn.ptit.moviebooking.booking.saga;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import vn.ptit.moviebooking.common.Command;
import vn.ptit.moviebooking.common.Event;

import java.util.List;
import java.util.UUID;

@Saga
@ProcessingGroup("booking-saga")
public class BookingSaga {

    @Autowired
    private transient CommandGateway commandGateway;
    private final Logger logger = LoggerFactory.getLogger(BookingSaga.class);

    @StartSaga
    @SagaEventHandler(associationProperty = "bookingId")
    public void on(Event.BookingCreatedEvent event) {
        logger.debug("Start booking saga");
        String seatReservationId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("bookingId", event.getBookingId());
        commandGateway.send(new Command.ReserveSeatCommand(
                seatReservationId,
                event.getSeatIds(),
                event.getShowId()
            ))
            .whenComplete((result, ex) -> {
                logger.debug("Send to movie service: {} - {}", result, seatReservationId);

                if (ex != null) {
                    System.err.println("Command failed: " + ex.getMessage());
                }
            });
    }

    @SagaEventHandler(associationProperty = "seatReservationId")
    public void on(Event.SeatReservedEvent event) {
        String paymenID = UUID.randomUUID().toString();
        logger.debug("Thanh toán command");
        commandGateway.send(new Command.ProcessPaymentCommand(paymenID, 1000.334f));
    }

    @SagaEventHandler(associationProperty = "seatReservationId")
    public void on(Event.SeatReservationFailedEvent event) {
        logger.debug("Giữ ghế thất bại command");
//        commandGateway.send(new Command.MarkBookingFailedCommand(event.getBookingId(), event.getReason()));
        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void on(Event.PaymentCompletedEvent event) {
        logger.debug("Thanh toán thành công command");
        commandGateway.send(new Command.MarkBookingConfirmedCommand());
        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void on(Event.PaymentFailedEvent event) {
        logger.debug("Thanh toán thất bại command");
        commandGateway.send(new Command.CancelSeatCommand("", List.of(1, 3)));
        commandGateway.send(new Command.MarkBookingFailedCommand());
        SagaLifecycle.end();
    }
}
