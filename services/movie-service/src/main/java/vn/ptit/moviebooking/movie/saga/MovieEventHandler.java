package vn.ptit.moviebooking.movie.saga;

import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.ptit.moviebooking.common.Event;
import vn.ptit.moviebooking.movie.service.SeatShowService;

@Component
public class MovieEventHandler {

    @Autowired
    private SeatShowService service;
    private final Logger logger = LoggerFactory.getLogger(MovieEventHandler.class);

    @EventHandler
    public void updateReserveSeats(Event.ReserveSeatEvent reserveSeatEvent) {
        service.updateSeats(reserveSeatEvent.getSeatIds(), "RESERVED");
        System.out.println("Update DB to reserve seats: " + reserveSeatEvent.getSeatIds());
    }

    @EventHandler
    public void updateCancelSeats(Event.ReserveSeatEvent reserveSeatEvent) {
        service.updateSeats(reserveSeatEvent.getSeatIds(), "AVAILABLE");
        System.out.println("Update DB to cancel seats: " + reserveSeatEvent.getSeatIds());
    }
}
