package vn.ptit.moviebooking.payment.saga;

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
        private String bookingId;
        private List<Integer> seatIds;
        private Integer showId;

        public ReserveSeatCommand() {
        }

        public ReserveSeatCommand(String bookingId, List<Integer> seatIds, Integer showId) {
            this.bookingId = bookingId;
            this.seatIds = seatIds;
            this.showId = showId;
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

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
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
        private String bookingId;
        private List<Integer> seatIds;

        public CancelSeatCommand() {
        }

        public CancelSeatCommand(String bookingId, List<Integer> seatIds) {
            this.bookingId = bookingId;
            this.seatIds = seatIds;
        }

        public String getBookingId() {
            return bookingId;
        }

        public List<Integer> getSeatIds() {
            return seatIds;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public void setSeatIds(List<Integer> seatIds) {
            this.seatIds = seatIds;
        }
    }

    // Payment
    public static class ProcessPaymentCommand {
        @TargetAggregateIdentifier
        private String bookingId;
        private Float amount;

        public ProcessPaymentCommand() {
        }

        public ProcessPaymentCommand(String bookingId, Float amount) {
            this.bookingId = bookingId;
            this.amount = amount;
        }

        public String getBookingId() {
            return bookingId;
        }

        public Float getAmount() {
            return amount;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }
    }
}
