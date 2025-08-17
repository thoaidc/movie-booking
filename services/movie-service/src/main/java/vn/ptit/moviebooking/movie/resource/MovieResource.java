package vn.ptit.moviebooking.movie.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.ptit.moviebooking.movie.dto.GetOrderInfoRequest;
import vn.ptit.moviebooking.movie.dto.request.BaseRequestDTO;
import vn.ptit.moviebooking.movie.dto.response.BaseResponseDTO;
import vn.ptit.moviebooking.movie.service.MovieService;

@RestController
@RequestMapping("/api/p/movies")
public class MovieResource {

    private final MovieService movieService;

    public MovieResource(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public BaseResponseDTO getAllMoviesWithPaging(@ModelAttribute BaseRequestDTO request) {
        return movieService.getAllMovieWithPaging(request);
    }

    @GetMapping("/{movieId}")
    public BaseResponseDTO getMovieDetail(@PathVariable Integer movieId) {
        return movieService.getMovieDetail(movieId);
    }

    @PostMapping("/info")
    public BaseResponseDTO getMovieOrderInfo(@RequestBody GetOrderInfoRequest request) {
        return movieService.getOrderMovieInfo(request);
    }
}
