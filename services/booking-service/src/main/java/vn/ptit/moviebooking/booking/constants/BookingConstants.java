package vn.ptit.moviebooking.booking.constants;

public interface BookingConstants {

    interface Status {
        String PENDING = "PENDING";
        String RESERVE_SEATS_SUCCESS = "RESERVE_SEATS_SUCCESS";
        String RESERVE_SEATS_FAILED = "RESERVE_SEATS_FAILED";
        String PAYMENT_SUCCESS = "PAYMENT_SUCCESS";
        String PAYMENT_FAILED = "PAYMENT_FAILED";
        String COMPLETED = "COMPLETED";
        String FAILED = "FAILED";
    }
}
