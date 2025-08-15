package vn.ptit.moviebooking.common;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

public class Command {

    // Booking
    public static class CreateBookingCommand {
        @TargetAggregateIdentifier
        private String bookingId;
        private List<Integer> seatIds;
        private Integer showId;
        private Float amount;

        public CreateBookingCommand() {
        }

        public CreateBookingCommand(String bookingId, List<Integer> seatIds, Integer showId, Float amount) {
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

    public static class MarkBookingConfirmedCommand {
        @TargetAggregateIdentifier
        private String bookingId;

        public MarkBookingConfirmedCommand() {
        }

        public MarkBookingConfirmedCommand(String bookingId) {
            this.bookingId = bookingId;
        }

        public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }
    }

    public static class MarkBookingFailedCommand {
        @TargetAggregateIdentifier
        private String bookingId;
        private String reason;

        public MarkBookingFailedCommand() {
        }

        public MarkBookingFailedCommand(String bookingId, String reason) {
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
    public static class ReserveSeatCommand {
        @TargetAggregateIdentifier
        private String seatReservationId;
        private List<Integer> seatIds;
        private Integer showId;

        public ReserveSeatCommand() {
        }

        public ReserveSeatCommand(String seatReservationId, List<Integer> seatIds, Integer showId) {
            this.seatReservationId = seatReservationId;
            this.seatIds = seatIds;
            this.showId = showId;
        }

        public String getSeatReservationId() {
            return seatReservationId;
        }

        public void setSeatReservationId(String seatReservationId) {
            this.seatReservationId = seatReservationId;
        }

        public List<Integer> getSeatIds() {
            return seatIds;
        }

        public Integer getShowId() {
            return showId;
        }

        public void setSeatIds(List<Integer> seatIds) {
            this.seatIds = seatIds;
        }

        public void setShowId(Integer showId) {
            this.showId = showId;
        }
    }

    public static class CancelSeatCommand {
        @TargetAggregateIdentifier
        private String seatReservationId;
        private List<Integer> seatIds;

        public CancelSeatCommand() {
        }

        public CancelSeatCommand(String seatReservationId, List<Integer> seatIds) {
            this.seatReservationId = seatReservationId;
            this.seatIds = seatIds;
        }

        public String getSeatReservationId() {
            return seatReservationId;
        }

        public void setSeatReservationId(String seatReservationId) {
            this.seatReservationId = seatReservationId;
        }

        public List<Integer> getSeatIds() {
            return seatIds;
        }

        public void setSeatIds(List<Integer> seatIds) {
            this.seatIds = seatIds;
        }
    }

    // Payment
    public static class ProcessPaymentCommand {
        @TargetAggregateIdentifier
        private String paymentId;
        private Float amount;

        public ProcessPaymentCommand() {
        }

        public ProcessPaymentCommand(String paymentId, Float amount) {
            this.paymentId = paymentId;
            this.amount = amount;
        }

        public String getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(String paymentId) {
            this.paymentId = paymentId;
        }

        public Float getAmount() {
            return amount;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }
    }
}
