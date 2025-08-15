package vn.ptit.moviebooking.booking.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.ptit.moviebooking.booking.dto.request.BookingRequest;
import vn.ptit.moviebooking.common.Command;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/p/bookings")
public class BookingController {

    private final CommandGateway commandGateway;

    public BookingController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public CompletableFuture<String> createBooking(@RequestBody BookingRequest req) {
        String bookingId = UUID.randomUUID().toString();
        return commandGateway.send(new Command.CreateBookingCommand(
            bookingId, req.getSeatIds(), req.getShowId(), req.getTotalAmount()
        ));
    }
}
