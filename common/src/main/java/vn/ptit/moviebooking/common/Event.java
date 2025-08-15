package vn.ptit.moviebooking.common;

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
        private String seatReservationId;

        public String getSeatReservationId() {
            return seatReservationId;
        }

        public void setSeatReservationId(String seatReservationId) {
            this.seatReservationId = seatReservationId;
        }

        public SeatReservedEvent() {
        }

        public SeatReservedEvent(String seatReservationId) {
            this.seatReservationId = seatReservationId;
        }
    }

    public static class SeatReservationFailedEvent {
        private String seatReservationId;
        private String reason;

        public SeatReservationFailedEvent() {
        }

        public SeatReservationFailedEvent(String seatReservationId, String reason) {
            this.seatReservationId = seatReservationId;
            this.reason = reason;
        }

        public String getSeatReservationId() {
            return seatReservationId;
        }

        public void setSeatReservationId(String seatReservationId) {
            this.seatReservationId = seatReservationId;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    // Payment
    public static class PaymentCompletedEvent {
        private String paymentId;

        public PaymentCompletedEvent() {
        }

        public PaymentCompletedEvent(String paymentId) {
            this.paymentId = paymentId;
        }

        public String getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(String paymentId) {
            this.paymentId = paymentId;
        }
    }

    public static class PaymentFailedEvent {
        private String paymentId;
        private String reason;

        public PaymentFailedEvent() {
        }

        public PaymentFailedEvent(String paymentId, String reason) {
            this.paymentId = paymentId;
            this.reason = reason;
        }

        public String getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(String paymentId) {
            this.paymentId = paymentId;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
