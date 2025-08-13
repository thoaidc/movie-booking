package vn.ptit.moviebooking.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.ptit.moviebooking.booking.entity.BookingSeat;

import java.util.List;

@Repository
public interface BookingSeatRepository extends JpaRepository<BookingSeat, Integer> {

    @Query(value = "SELECT bs.seat_id FROM booking_seat bs WHERE bs.booking_id = ?1", nativeQuery = true)
    List<Integer> findAllSeatIdsByBookingId(Integer bookingId);
}
