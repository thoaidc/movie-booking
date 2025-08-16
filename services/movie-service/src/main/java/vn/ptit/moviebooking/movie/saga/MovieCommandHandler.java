package vn.ptit.moviebooking.movie.saga;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;
import vn.ptit.moviebooking.common.Command;
import vn.ptit.moviebooking.movie.service.SeatShowService;

@Component
public class MovieCommandHandler {

    private final SeatShowService service;
    private final CommandGateway commandGateway;

    public MovieCommandHandler(SeatShowService service, CommandGateway commandGateway) {
        this.service = service;
        this.commandGateway = commandGateway;
    }

    @CommandHandler
    public void handle(Command.CheckAndReserveSeatCommand command) {
        boolean success = service.checkSeatAndReserve(command.getSeatIds());
        Command.ReserveSeatResultCommand reserveSeatResultCommand = new Command.ReserveSeatResultCommand();
        reserveSeatResultCommand.setBookingId(command.getBookingId());
        reserveSeatResultCommand.setSeatReservationId(command.getSeatReservationId());
        reserveSeatResultCommand.setSeatIds(command.getSeatIds());
        reserveSeatResultCommand.setSuccess(success);
        reserveSeatResultCommand.setTotalAmount(command.getTotalAmount());
        System.out.println("Movie command handler xử lý command kiểm tra và giữ ghế: " + success);
        commandGateway.send(reserveSeatResultCommand);
    }

    @CommandHandler
    public void handle(Command.CancelSeatCommand command) {
        service.releasedBookedSeats(command.getSeatIds());
        System.out.println("Movie command handler xử lý command phát hành lại ghế: " + command.getSeatIds());
        Command.CancelSeatResultCommand cancelResultCommand = new Command.CancelSeatResultCommand();
        cancelResultCommand.setBookingId(command.getBookingId());
        cancelResultCommand.setSeatReservationId(command.getSeatReservationId());
        cancelResultCommand.setSeatIds(command.getSeatIds());
        cancelResultCommand.setTotalAmount(command.getTotalAmount());
        commandGateway.send(cancelResultCommand);
    }
}
