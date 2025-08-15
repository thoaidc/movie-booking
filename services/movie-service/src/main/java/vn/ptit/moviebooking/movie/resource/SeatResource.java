package vn.ptit.moviebooking.movie.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.ptit.moviebooking.movie.dto.response.BaseResponseDTO;
import vn.ptit.moviebooking.movie.service.MovieService;
import vn.ptit.moviebooking.movie.service.SeatShowService;

@RestController
@RequestMapping("/api/p/seats")
public class SeatResource {

    private final MovieService movieService;
    private final SeatShowService seatShowService;

    public SeatResource(MovieService movieService, SeatShowService seatShowService) {
        this.movieService = movieService;
        this.seatShowService = seatShowService;
    }

    @GetMapping("/by-show/{showId}")
    public BaseResponseDTO getAllSeatsByShowId(@PathVariable Integer showId) {
        return movieService.getAllSeatsByShowId(showId);
    }

    @GetMapping("/by-show2/{showId}")
    public BaseResponseDTO getAllSeatsOfShow(@PathVariable Integer showId) {
        return seatShowService.getAllSeatsOfShow(showId);
    }
}
