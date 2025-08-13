package vn.ptit.moviebooking.movie.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.moviebooking.movie.constants.HttpStatusConstants;
import vn.ptit.moviebooking.movie.constants.SeatsConstants;
import vn.ptit.moviebooking.movie.dto.request.SeatsCommand;
import vn.ptit.moviebooking.movie.dto.response.BaseResponseDTO;
import vn.ptit.moviebooking.movie.entity.SeatShow;
import vn.ptit.moviebooking.movie.repository.SeatShowRepository;

import java.util.List;

@Service
public class CheckSeatAvailabilityService {

    private final SeatShowRepository seatShowRepository;

    public CheckSeatAvailabilityService(SeatShowRepository seatShowRepository) {
        this.seatShowRepository = seatShowRepository;
    }

    public BaseResponseDTO getAllSeatsOfShow(Integer showId) {
        return BaseResponseDTO.builder().ok(seatShowRepository.findAllByShowId(showId));
    }

    @Transactional
    public BaseResponseDTO checkSeatsAvailability(SeatsCommand command) {
        List<SeatShow> seatShows = seatShowRepository
                .findAllByIdInAndStatusForUpdate(command.getSeatIds(), SeatsConstants.Status.AVAILABLE);

        if (seatShows.size() == command.getSeatIds().size()) {
            seatShows.forEach(seat -> seat.setStatus(SeatsConstants.Status.RESERVED));
            seatShowRepository.saveAll(seatShows);
            return BaseResponseDTO.builder().ok();
        }

        return BaseResponseDTO.builder().code(HttpStatusConstants.BAD_REQUEST).success(false).build();
    }

    @Transactional
    public BaseResponseDTO confirmBookedSeats(SeatsCommand command) {
        List<SeatShow> seatShows = seatShowRepository.findAllById(command.getSeatIds());
        seatShows.forEach(seat -> seat.setStatus(SeatsConstants.Status.BOOKED));
        seatShowRepository.saveAll(seatShows);
        return BaseResponseDTO.builder().ok();
    }

    @Transactional
    public BaseResponseDTO releasedBookedSeats(SeatsCommand command) {
        List<SeatShow> seatShows = seatShowRepository.findAllById(command.getSeatIds());
        seatShows.forEach(seat -> seat.setStatus(SeatsConstants.Status.AVAILABLE));
        seatShowRepository.saveAll(seatShows);
        return BaseResponseDTO.builder().ok();
    }
}
