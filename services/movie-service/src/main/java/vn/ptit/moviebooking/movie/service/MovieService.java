package vn.ptit.moviebooking.movie.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import vn.ptit.moviebooking.movie.constants.HttpStatusConstants;
import vn.ptit.moviebooking.movie.dto.request.BaseRequestDTO;
import vn.ptit.moviebooking.movie.dto.request.ValidateMovieRequest;
import vn.ptit.moviebooking.movie.dto.response.BaseResponseDTO;
import vn.ptit.moviebooking.movie.entity.Movie;
import vn.ptit.moviebooking.movie.entity.Show;
import vn.ptit.moviebooking.movie.repository.MovieRepository;
import vn.ptit.moviebooking.movie.repository.SeatRepository;
import vn.ptit.moviebooking.movie.repository.ShowRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;

    public MovieService(MovieRepository movieRepository,
                        ShowRepository showRepository,
                        SeatRepository seatRepository) {
        this.movieRepository = movieRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
    }

    public BaseResponseDTO validateMovieInfo(ValidateMovieRequest request) {
        Optional<Movie> movie = movieRepository.findById(request.getMovieId());
        Optional<Show> show = showRepository.findById(request.getShowId());

        if (movie.isPresent() && show.isPresent() && ZonedDateTime.now(ZoneId.systemDefault()).isBefore(show.get().getStartTime()))
            return BaseResponseDTO.builder().ok();

        return BaseResponseDTO.builder().code(HttpStatusConstants.BAD_REQUEST).success(false).build();
    }

    public BaseResponseDTO getAllMovieWithPaging(BaseRequestDTO request) {
        Page<Movie> moviesPaged = movieRepository.findAllWithPaging(
                request.getKeywordSearch(),
                request.getFromDateSearch(),
                request.getToDateSearch(),
                request.getPageable()
        );

        return BaseResponseDTO.builder().total(moviesPaged.getTotalElements()).ok(moviesPaged.getContent());
    }

    public BaseResponseDTO getMovieDetail(Integer movieId) {
        return BaseResponseDTO.builder().ok(movieRepository.findById(movieId));
    }

    public BaseResponseDTO getAllShowsByMovieId(Integer movieId) {
        return BaseResponseDTO.builder().ok(showRepository.findAllByMovieId(movieId));
    }

    public BaseResponseDTO getAllSeatsByShowId(Integer showId) {
        return BaseResponseDTO.builder().ok(seatRepository.findAllByShowId(showId));
    }
}
