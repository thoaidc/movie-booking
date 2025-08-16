package vn.ptit.moviebooking.common;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

public class Command {

    // Booking
    public static class CreateBookingCommand {
        @TargetAggregateIdentifier
        private String bookingId;
        private Float totalAmount;
        private List<Integer> seatIds;

        public CreateBookingCommand() {}

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

    public static class MarkBookingSuccessCommand {
        @TargetAggregateIdentifier
        private String bookingId;

        public MarkBookingSuccessCommand() {
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
    public static class ReserveSeatCommand {
        @TargetAggregateIdentifier
        private String seatReservationId;
        private String bookingId;
        private List<Integer> seatIds;

        public ReserveSeatCommand() {
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

    public static class CancelSeatCommand {
        @TargetAggregateIdentifier
        private String seatReservationId;
        private String bookingId;
        private List<Integer> seatIds;

        public CancelSeatCommand() {
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
    public static class CreatePaymentCommand {
        @TargetAggregateIdentifier
        private String paymentId;
        private String bookingId;
        private Float amount;

        public CreatePaymentCommand() {
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

    public static class ProcessPaymentCommand {
        @TargetAggregateIdentifier
        private String paymentId;
        private String bookingId;
        private Float amount;

        public ProcessPaymentCommand() {
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

    public static class ConfirmPaymentCommand {
        @TargetAggregateIdentifier
        private String paymentId;
        private String bookingId;
        private Float amount;

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


    public static class RefundCommand {
        @TargetAggregateIdentifier
        private String paymentId;
        private String bookingId;
        private Float amount;

        public RefundCommand() {
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
