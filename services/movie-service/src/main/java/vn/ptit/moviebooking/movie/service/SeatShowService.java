package vn.ptit.moviebooking.movie.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.ptit.moviebooking.movie.constants.SeatsConstants;
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
    public boolean checkSeatAndReserve(List<Integer> seats) {
        List<SeatShow> seatShows = seatShowRepository.findAllByIdInAndStatusForUpdate(seats, SeatsConstants.Status.AVAILABLE);

        if (seatShows != null && seatShows.size() == seats.size()) {
            seatShows.forEach(seat -> seat.setStatus(SeatsConstants.Status.BOOKED));
            seatShowRepository.saveAll(seatShows);
            return true;
        }

        return false;
    }

    public BaseResponseDTO getAllSeatsOfShow(Integer showId) {
        return BaseResponseDTO.builder().ok(seatShowRepository.findAllByShowId(showId));
    }

    @Transactional
    public void releasedBookedSeats(List<Integer> seatIds) {
        List<SeatShow> seatShows = seatShowRepository.findAllById(seatIds);
        seatShows.forEach(seat -> seat.setStatus(SeatsConstants.Status.AVAILABLE));
        seatShowRepository.saveAll(seatShows);
    }
}
