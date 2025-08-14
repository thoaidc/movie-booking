package vn.ptit.moviebooking.booking.saga;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Saga
public class BookingSaga {

    @Autowired
    private final CommandGateway commandGateway;

    public BookingSaga(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "bookingId")
    public void on(Event.BookingCreatedEvent event) {
        System.out.println("Start booking saga");
        SagaLifecycle.associateWith("bookingId", event.getBookingId());
        commandGateway.send(new Command.ReserveSeatCommand(event.getBookingId(), event.getSeatIds(), event.getShowId()));
    }

    @SagaEventHandler(associationProperty = "bookingId")
    public void on(Event.SeatReservedEvent event) {
        System.out.println("Thanh toán command");
        commandGateway.send(new Command.ProcessPaymentCommand(event.getBookingId(), 1000.334f));
    }

    @SagaEventHandler(associationProperty = "bookingId")
    public void on(Event.SeatReservationFailedEvent event) {
        System.out.println("Giữ ghế thất bại command");
        commandGateway.send(new Command.MarkBookingFailedCommand(event.getBookingId(), event.getReason()));
        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "bookingId")
    public void on(Event.PaymentCompletedEvent event) {
        System.out.println("Thanh toán thành công command");
        commandGateway.send(new Command.MarkBookingConfirmedCommand(event.getBookingId()));
        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "bookingId")
    public void on(Event.PaymentFailedEvent event) {
        System.out.println("Thanh toán thất bại command");
        commandGateway.send(new Command.CancelSeatCommand(event.getBookingId(), List.of(1, 3)));
        commandGateway.send(new Command.MarkBookingFailedCommand(event.getBookingId(), event.getReason()));
        SagaLifecycle.end();
    }
}
