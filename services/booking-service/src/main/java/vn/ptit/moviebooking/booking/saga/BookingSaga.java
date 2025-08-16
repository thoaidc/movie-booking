package vn.ptit.moviebooking.booking.saga;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import vn.ptit.moviebooking.booking.constants.BookingConstants;
import vn.ptit.moviebooking.booking.dto.PaymentDTO;
import vn.ptit.moviebooking.booking.dto.request.PaymentRequest;
import vn.ptit.moviebooking.booking.dto.response.BaseResponseDTO;
import vn.ptit.moviebooking.booking.service.PaymentServiceClient;
import vn.ptit.moviebooking.booking.service.TicketBookingService;
import vn.ptit.moviebooking.common.Command;
import vn.ptit.moviebooking.common.Event;

import java.util.UUID;

@Saga
@ProcessingGroup("booking-saga")
public class BookingSaga {

    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private PaymentServiceClient paymentServiceClient;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TicketBookingService ticketBookingService;

    public BookingSaga() {}

    @StartSaga
    @SagaEventHandler(associationProperty = "bookingId")
    public void on(Event.CreateBookingEvent event) {
        // tao check seat aggregate
        String seatReservationId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("seatReservationId", seatReservationId);
        Command.CreateCheckSeatCommand command = new Command.CreateCheckSeatCommand();
        command.setBookingId(event.getBookingId());
        command.setSeatReservationId(seatReservationId);
        command.setSeatIds(event.getSeatIds());
        commandGateway.send(command);
        System.out.println("Start saga: " + event.getBookingId());
    }

    @SagaEventHandler(associationProperty = "seatReservationId")
    public void handle(Event.CreateCheckSeatEvent event) {
        // check and reserve seat
        Command.CheckAndReserveSeatCommand command = new Command.CheckAndReserveSeatCommand();
        command.setBookingId(event.getBookingId());
        command.setSeatReservationId(event.getSeatReservationId());
        command.setSeatIds(event.getSeatIds());
        commandGateway.send(command);
        System.out.println("Gửi command tạo check seat aggregate");
    }

    @SagaEventHandler(associationProperty = "seatReservationId")
    public void handle(Event.ReserveSeatResultEvent event) {
        if (event.isSuccess()) { // giu ghe thanh cong
            ticketBookingService.updateBookingStatus(event.getBookingId(), BookingConstants.Status.RESERVE_SEATS_SUCCESS);
            // tao thanh toan aggregate
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setBookingId(event.getBookingId());
            paymentRequest.setAmount(event.getAmount());
            BaseResponseDTO responseDTO = paymentServiceClient.createPaymentPending(paymentRequest);
            PaymentDTO paymentDTO = mapper.convertValue(responseDTO.getResult(), PaymentDTO.class);

            SagaLifecycle.associateWith("paymentId", paymentDTO.getId());
            Command.CreatePaymentCommand createPaymentCommand = new Command.CreatePaymentCommand();
            createPaymentCommand.setBookingId(event.getBookingId());
            createPaymentCommand.setPaymentId(paymentDTO.getId());
            createPaymentCommand.setAmount(event.getAmount());
            createPaymentCommand.setSeatIds(event.getSeatIds());
            commandGateway.send(createPaymentCommand);
            System.out.println("Saga giữ ghế thành công, gửi command tạo thanh toán aggregate: "+ event.getSeatReservationId());
        } else {
            // huy booking
            Command.MarkBookingFailedCommand bookingFailedCommand = new Command.MarkBookingFailedCommand();
            bookingFailedCommand.setBookingId(event.getBookingId());
            bookingFailedCommand.setReason("Giu ghe that bai");
            bookingFailedCommand.setStatus(BookingConstants.Status.RESERVE_SEATS_FAILED);
            commandGateway.send(bookingFailedCommand);
            System.out.println("Saga giữ ghế thất bại, gửi command hủy đơn hàng: " + event.getSeatReservationId());
        }
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void handle(Event.CreatePaymentEvent event) {
        // thanh toan
        Command.ProcessPaymentCommand processPaymentCommand = new Command.ProcessPaymentCommand();
        processPaymentCommand.setBookingId(event.getBookingId());
        processPaymentCommand.setPaymentId(event.getPaymentId());
        processPaymentCommand.setAmount(event.getAmount());
        processPaymentCommand.setSeatIds(event.getSeatIds());
        commandGateway.send(processPaymentCommand);
        System.out.println("Saga tạo thanh toán aggregate thành công, gửi command tiến hành thanh toán: " +  event.getPaymentId());
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void handle(Event.PaymentResultEvent event) {
        // ket qua thanh toan
        if (event.isSuccess()) {
            ticketBookingService.updateBookingStatus(event.getBookingId(), BookingConstants.Status.PAYMENT_SUCCESS);
            // xac nhan don hang thanh cong
            Command.MarkBookingSuccessCommand bookingSuccessCommand = new Command.MarkBookingSuccessCommand();
            bookingSuccessCommand.setBookingId(event.getBookingId());
            commandGateway.send(bookingSuccessCommand);
            System.out.println("Saga thanh toán thành công, gửi command xác nhận đơn hàng thành công: "+ event.getPaymentId());
        } else {
            // thanh toan loi, huy giu ghe va cancel don hang
            Command.CancelSeatCommand cancelSeatCommand = new Command.CancelSeatCommand();
            cancelSeatCommand.setBookingId(event.getBookingId());
            cancelSeatCommand.setSeatIds(event.getSeatIds());
            Command.MarkBookingFailedCommand bookingFailedCommand = new Command.MarkBookingFailedCommand();
            bookingFailedCommand.setPaymentId(event.getPaymentId());
            bookingFailedCommand.setBookingId(event.getBookingId());
            bookingFailedCommand.setReason("Thanh toán thất bại");
            bookingFailedCommand.setStatus(BookingConstants.Status.PAYMENT_FAILED);
            commandGateway.send(cancelSeatCommand);
            System.out.println("Saga thanh toán thất bại, gửi command hủy giữ ghế: " + event.getSeatIds());
            commandGateway.send(bookingFailedCommand);
            System.out.println("Saga thanh toán thất hại, gửi command hủy đơn hàng: " +  event.getPaymentId());
        }
    }

    @SagaEventHandler(associationProperty = "bookingId")
    @EndSaga
    public void handle(Event.MarkBookingSuccessEvent event) {
        System.out.println("Booking successfully! End Saga.");
        // update DB
        ticketBookingService.updateBookingStatus(event.getBookingId(), BookingConstants.Status.COMPLETED);
    }

    @SagaEventHandler(associationProperty = "bookingId")
    @EndSaga
    public void handle(Event.MarkBookingFailedEvent event) {
        // Neu thanh toan roi thi hoan tien
        if (event.getPaymentId() != null) {
            ticketBookingService.updateBookingStatus(event.getBookingId(), BookingConstants.Status.FAILED);
            Command.RefundCommand refundCommand = new Command.RefundCommand();
            refundCommand.setPaymentId(event.getPaymentId());
            refundCommand.setBookingId(refundCommand.getBookingId());
            refundCommand.setReason(event.getReason());
            commandGateway.send(refundCommand);
            System.out.println("Saga gửi command hoàn tiền");
        } else {
            ticketBookingService.updateBookingStatus(event.getBookingId(), event.getStatus());
        }

        System.out.println("Booking failed! End Saga.");
    }
}
