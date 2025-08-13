package vn.ptit.moviebooking.movie.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.ptit.moviebooking.movie.dto.response.BaseResponseDTO;
import vn.ptit.moviebooking.movie.service.CheckSeatAvailabilityService;
import vn.ptit.moviebooking.movie.service.MovieService;

@RestController
@RequestMapping("/api/seats")
public class SeatResource {

    private final MovieService movieService;
    private final CheckSeatAvailabilityService checkSeatAvailabilityService;

    public SeatResource(MovieService movieService, CheckSeatAvailabilityService checkSeatAvailabilityService) {
        this.movieService = movieService;
        this.checkSeatAvailabilityService = checkSeatAvailabilityService;
    }

    @GetMapping("/by-all-shows")
    public BaseResponseDTO getAllSeatsGroupedByShow() {
        return movieService.getAllSeatsGroupedByShow();
    }

    @GetMapping("/by-show/{showId}")
    public BaseResponseDTO getAllSeatsByShowId(@PathVariable Integer showId) {
        return movieService.getAllSeatsByShowId(showId);
    }

    @GetMapping("/by-show2/{showId}")
    public BaseResponseDTO getAllSeatsOfShow(@PathVariable Integer showId) {
        return checkSeatAvailabilityService.getAllSeatsOfShow(showId);
    }
}
