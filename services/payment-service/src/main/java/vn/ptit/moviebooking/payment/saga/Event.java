package vn.ptit.moviebooking.payment.saga;

import java.util.List;

public class Event {

    // Booking
    public static class BookingCreatedEvent {
        private String bookingId;
        private List<Integer> seatIds;
        private Integer showId;
        private Float amount;

        public BookingCreatedEvent() {
        }

        public BookingCreatedEvent(String bookingId, List<Integer> seatIds, Integer showId, Float amount) {
            this.bookingId = bookingId;
            this.seatIds = seatIds;
            this.showId = showId;
            this.amount = amount;
        }

        public String getBookingId() {
            return bookingId;
        }

        public List<Integer> getSeatIds() {
            return seatIds;
        }

        public Integer getShowId() {
            return showId;
        }

        public Float getAmount() {
            return amount;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public void setSeatIds(List<Integer> seatIds) {
            this.seatIds = seatIds;
        }

        public void setShowId(Integer showId) {
            this.showId = showId;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }
    }

    public static class BookingConfirmedEvent {
        private String bookingId;

        public BookingConfirmedEvent() {
        }

        public BookingConfirmedEvent(String bookingId) {
            this.bookingId = bookingId;
        }

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }
    }

    public static class BookingFailedEvent {
        private String bookingId;
        private String reason;

        public BookingFailedEvent() {
        }

        public BookingFailedEvent(String bookingId, String reason) {
            this.bookingId = bookingId;
            this.reason = reason;
        }

        public String getBookingId() {
            return bookingId;
        }

        public String getReason() {
            return reason;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    // Movie
    public static class SeatReservedEvent {
        private String bookingId;

        public SeatReservedEvent() {
        }

        public SeatReservedEvent(String bookingId) {
            this.bookingId = bookingId;
        }

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }
    }

    public static class SeatReservationFailedEvent {
        private String bookingId;
        private String reason;

        public SeatReservationFailedEvent() {
        }

        public SeatReservationFailedEvent(String bookingId, String reason) {
            this.bookingId = bookingId;
            this.reason = reason;
        }

        public String getBookingId() {
            return bookingId;
        }

        public String getReason() {
            return reason;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    // Payment
    public static class PaymentCompletedEvent {
        private String bookingId;

        public PaymentCompletedEvent() {
        }

        public PaymentCompletedEvent(String bookingId) {
            this.bookingId = bookingId;
        }

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }
    }

    public static class PaymentFailedEvent {
        private String bookingId;
        private String reason;

        public PaymentFailedEvent() {
        }

        public PaymentFailedEvent(String bookingId, String reason) {
            this.bookingId = bookingId;
            this.reason = reason;
        }

        public String getBookingId() {
            return bookingId;
        }

        public String getReason() {
            return reason;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
