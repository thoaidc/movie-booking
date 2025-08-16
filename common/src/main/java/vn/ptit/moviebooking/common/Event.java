package vn.ptit.moviebooking.common;

import java.util.List;

public class Event {

    // Booking
    public static class CreateBookingEvent {
        private String bookingId;
        private Float totalAmount;
        private List<Integer> seatIds;

        public CreateBookingEvent() {}

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public Float getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Float totalAmount) {
            this.totalAmount = totalAmount;
        }

        public List<Integer> getSeatIds() {
            return seatIds;
        }

        public void setSeatIds(List<Integer> seatIds) {
            this.seatIds = seatIds;
        }
    }

    public static class MarkBookingSuccessEvent {
        private String bookingId;

        public MarkBookingSuccessEvent() {
        }

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }
    }

    public static class MarkBookingFailedEvent {
        private String bookingId;
        private String reason;

        public MarkBookingFailedEvent() {
        }

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    // Movie
    public static class ReserveSeatEvent {
        private String seatReservationId;
        private String bookingId;
        private Float amount;
        private List<Integer> seatIds;

        public ReserveSeatEvent() {
        }

        public Float getAmount() {
            return amount;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }

        public String getSeatReservationId() {
            return seatReservationId;
        }

        public void setSeatReservationId(String seatReservationId) {
            this.seatReservationId = seatReservationId;
        }

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public List<Integer> getSeatIds() {
            return seatIds;
        }

        public void setSeatIds(List<Integer> seatIds) {
            this.seatIds = seatIds;
        }
    }

    public static class CancelSeatEvent {
        private String seatReservationId;
        private String bookingId;
        private List<Integer> seatIds;

        public CancelSeatEvent() {
        }

        public String getSeatReservationId() {
            return seatReservationId;
        }

        public void setSeatReservationId(String seatReservationId) {
            this.seatReservationId = seatReservationId;
        }

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public List<Integer> getSeatIds() {
            return seatIds;
        }

        public void setSeatIds(List<Integer> seatIds) {
            this.seatIds = seatIds;
        }
    }

    // Payment
    public static class CreatePaymentEvent {
        private String paymentId;
        private String bookingId;
        private Float amount;

        public CreatePaymentEvent() {
        }

        public String getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(String paymentId) {
            this.paymentId = paymentId;
        }

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public Float getAmount() {
            return amount;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }
    }

    public static class ProcessPaymentEvent {
        private String paymentId;
        private String bookingId;
        private Float amount;

        public ProcessPaymentEvent() {
        }

        public String getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(String paymentId) {
            this.paymentId = paymentId;
        }

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public Float getAmount() {
            return amount;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }
    }

    public static class ConfirmPaymentEvent {
        private String paymentId;
        private String bookingId;
        private Float amount;

        public ConfirmPaymentEvent() {
        }

        public String getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(String paymentId) {
            this.paymentId = paymentId;
        }

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public Float getAmount() {
            return amount;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }
    }

    public static class RefundEvent {
        private String paymentId;
        private String bookingId;
        private Float amount;

        public RefundEvent() {
        }

        public String getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(String paymentId) {
            this.paymentId = paymentId;
        }

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public Float getAmount() {
            return amount;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }
    }
}
