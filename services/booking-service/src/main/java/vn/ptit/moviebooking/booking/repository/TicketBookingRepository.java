package vn.ptit.moviebooking.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.ptit.moviebooking.booking.dto.OrderDTO;
import vn.ptit.moviebooking.booking.entity.Booking;

import java.util.List;

@Repository
public interface TicketBookingRepository extends JpaRepository<Booking, Integer> {

    @Query(
        value = """
            select
                b.id,
                b.show_id as showId,
                b.total_amount as total,
                DATE_FORMAT(b.create_time, '%Y-%m-%d %H:%i:%s') as orderTime,
                b.status
            from `mb_booking`.`booking` b
            where b.user_id = ?1
        """,
        nativeQuery = true
    )
    List<OrderDTO> getAllOrders(Integer userId);
}
