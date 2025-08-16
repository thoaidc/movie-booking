package vn.ptit.moviebooking.booking.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.ptit.moviebooking.booking.dto.request.BookingRequest;
import vn.ptit.moviebooking.booking.dto.response.BaseResponseDTO;
import vn.ptit.moviebooking.booking.entity.Booking;
import vn.ptit.moviebooking.booking.service.TicketBookingService;
import vn.ptit.moviebooking.common.Command;

@RestController
@RequestMapping("/api/p/bookings")
public class BookingController {

    private final CommandGateway commandGateway;
    private final TicketBookingService bookingService;

    public BookingController(CommandGateway commandGateway, TicketBookingService bookingService) {
        this.commandGateway = commandGateway;
        this.bookingService = bookingService;
    }

    @PostMapping
    public BaseResponseDTO createBooking(@RequestBody BookingRequest bookingRequest) {
        Booking booking = bookingService.createBooking(bookingRequest);
        Command.CreateBookingCommand createBookingCommand = new Command.CreateBookingCommand();
        createBookingCommand.setBookingId(booking.getId());
        createBookingCommand.setSeatIds(bookingRequest.getSeatIds());
        createBookingCommand.setTotalAmount(bookingRequest.getTotalAmount());
        System.out.println("Create new booking: " + booking.getId());
        commandGateway.sendAndWait(createBookingCommand);
        return BaseResponseDTO.builder().ok(booking.getId());
    }
}
