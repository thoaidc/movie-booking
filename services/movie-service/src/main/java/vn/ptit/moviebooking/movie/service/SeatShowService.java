package vn.ptit.moviebooking.movie.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.moviebooking.movie.constants.SeatsConstants;
import vn.ptit.moviebooking.movie.dto.request.SeatsCommand;
import vn.ptit.moviebooking.movie.dto.response.BaseResponseDTO;
import vn.ptit.moviebooking.movie.entity.SeatShow;
import vn.ptit.moviebooking.movie.repository.SeatShowRepository;

import java.util.List;

@Service
public class SeatShowService {

    private final SeatShowRepository seatShowRepository;

    public SeatShowService(SeatShowRepository seatShowRepository) {
        this.seatShowRepository = seatShowRepository;
    }

    @Transactional
    public void updateSeats(List<Integer> seatShowIds, String status) {
        seatShowRepository.updateSeatsStatus(seatShowIds, status);
    }

    public BaseResponseDTO getAllSeatsOfShow(Integer showId) {
        return BaseResponseDTO.builder().ok(seatShowRepository.findAllByShowId(showId));
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
