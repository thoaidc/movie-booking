package vn.ptit.moviebooking.common;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

public class Command {

    // Booking
    public static class CreateBookingCommand {
        @TargetAggregateIdentifier
        private Integer bookingId;
        private Float totalAmount;
        private List<Integer> seatIds;

        public CreateBookingCommand() {}

        public Integer getBookingId() {
            return bookingId;
        }

        public void setBookingId(Integer bookingId) {
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
        private Integer bookingId;

        public MarkBookingSuccessCommand() {
        }

        public Integer getBookingId() {
            return bookingId;
        }

        public void setBookingId(Integer bookingId) {
            this.bookingId = bookingId;
        }
    }

    public static class MarkBookingFailedCommand {
        @TargetAggregateIdentifier
        private Integer bookingId;
        private Integer paymentId;
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(Integer paymentId) {
            this.paymentId = paymentId;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        private String reason;

        public MarkBookingFailedCommand() {
        }

        public Integer getBookingId() {
            return bookingId;
        }

        public void setBookingId(Integer bookingId) {
            this.bookingId = bookingId;
        }
    }

    // Movie
    public static class CreateCheckSeatCommand {
        @TargetAggregateIdentifier
        private String seatReservationId;
        private Integer bookingId;
        private List<Integer> seatIds;

        public CreateCheckSeatCommand() {
        }

        public String getSeatReservationId() {
            return seatReservationId;
        }

        public void setSeatReservationId(String seatReservationId) {
            this.seatReservationId = seatReservationId;
        }

        public Integer getBookingId() {
            return bookingId;
        }

        public void setBookingId(Integer bookingId) {
            this.bookingId = bookingId;
        }

        public List<Integer> getSeatIds() {
            return seatIds;
        }

        public void setSeatIds(List<Integer> seatIds) {
            this.seatIds = seatIds;
        }
    }

    public static class CheckAndReserveSeatCommand {
        @TargetAggregateIdentifier
        private String seatReservationId;
        private Integer bookingId;
        private List<Integer> seatIds;

        public CheckAndReserveSeatCommand() {
        }

        public String getSeatReservationId() {
            return seatReservationId;
        }

        public void setSeatReservationId(String seatReservationId) {
            this.seatReservationId = seatReservationId;
        }

        public Integer getBookingId() {
            return bookingId;
        }

        public void setBookingId(Integer bookingId) {
            this.bookingId = bookingId;
        }

        public List<Integer> getSeatIds() {
            return seatIds;
        }

        public void setSeatIds(List<Integer> seatIds) {
            this.seatIds = seatIds;
        }
    }

    public static class ReserveSeatResultCommand {
        @TargetAggregateIdentifier
        private String seatReservationId;
        private Integer bookingId;
        private List<Integer> seatIds;
        private boolean success;

        public ReserveSeatResultCommand() {
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getSeatReservationId() {
            return seatReservationId;
        }

        public void setSeatReservationId(String seatReservationId) {
            this.seatReservationId = seatReservationId;
        }

        public Integer getBookingId() {
            return bookingId;
        }

        public void setBookingId(Integer bookingId) {
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
        private Integer bookingId;
        private List<Integer> seatIds;

        public CancelSeatCommand() {
        }

        public String getSeatReservationId() {
            return seatReservationId;
        }

        public void setSeatReservationId(String seatReservationId) {
            this.seatReservationId = seatReservationId;
        }

        public Integer getBookingId() {
            return bookingId;
        }

        public void setBookingId(Integer bookingId) {
            this.bookingId = bookingId;
        }

        public List<Integer> getSeatIds() {
            return seatIds;
        }

        public void setSeatIds(List<Integer> seatIds) {
            this.seatIds = seatIds;
        }
    }

    public static class CancelSeatResultCommand {
        @TargetAggregateIdentifier
        private String seatReservationId;
        private Integer bookingId;
        private List<Integer> seatIds;

        public CancelSeatResultCommand() {
        }

        public String getSeatReservationId() {
            return seatReservationId;
        }

        public void setSeatReservationId(String seatReservationId) {
            this.seatReservationId = seatReservationId;
        }

        public Integer getBookingId() {
            return bookingId;
        }

        public void setBookingId(Integer bookingId) {
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
        private Integer paymentId;
        private Integer bookingId;
        private Float amount;
        private List<Integer> seatIds;

        public List<Integer> getSeatIds() {
            return seatIds;
        }

        public void setSeatIds(List<Integer> seatIds) {
            this.seatIds = seatIds;
        }

        public CreatePaymentCommand() {
        }

        public Integer getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(Integer paymentId) {
            this.paymentId = paymentId;
        }

        public Integer getBookingId() {
            return bookingId;
        }

        public void setBookingId(Integer bookingId) {
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
        private Integer paymentId;
        private Integer bookingId;
        private Float amount;
        private List<Integer> seatIds;

        public List<Integer> getSeatIds() {
            return seatIds;
        }

        public void setSeatIds(List<Integer> seatIds) {
            this.seatIds = seatIds;
        }

        public ProcessPaymentCommand() {
        }

        public Integer getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(Integer paymentId) {
            this.paymentId = paymentId;
        }

        public Integer getBookingId() {
            return bookingId;
        }

        public void setBookingId(Integer bookingId) {
            this.bookingId = bookingId;
        }

        public Float getAmount() {
            return amount;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }
    }

    public static class ProcessPaymentResultCommand {
        @TargetAggregateIdentifier
        private Integer paymentId;
        private Integer bookingId;
        private Float amount;
        private boolean success;
        private List<Integer> seatIds;

        public List<Integer> getSeatIds() {
            return seatIds;
        }

        public void setSeatIds(List<Integer> seatIds) {
            this.seatIds = seatIds;
        }

        public ProcessPaymentResultCommand() {
        }

        public Integer getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(Integer paymentId) {
            this.paymentId = paymentId;
        }

        public Integer getBookingId() {
            return bookingId;
        }

        public void setBookingId(Integer bookingId) {
            this.bookingId = bookingId;
        }

        public Float getAmount() {
            return amount;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }


    public static class RefundCommand {
        @TargetAggregateIdentifier
        private Integer paymentId;
        private Integer bookingId;
        private Float amount;
        private String reason;
        private List<Integer> seatIds;

        public List<Integer> getSeatIds() {
            return seatIds;
        }

        public void setSeatIds(List<Integer> seatIds) {
            this.seatIds = seatIds;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public RefundCommand() {
        }

        public Integer getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(Integer paymentId) {
            this.paymentId = paymentId;
        }

        public Integer getBookingId() {
            return bookingId;
        }

        public void setBookingId(Integer bookingId) {
            this.bookingId = bookingId;
        }

        public Float getAmount() {
            return amount;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }
    }

    public static class RefundResultCommand {
        @TargetAggregateIdentifier
        private Integer paymentId;
        private Integer bookingId;
        private Float amount;
        private String reason;
        private List<Integer> seatIds;

        public List<Integer> getSeatIds() {
            return seatIds;
        }

        public void setSeatIds(List<Integer> seatIds) {
            this.seatIds = seatIds;
        }

        public RefundResultCommand() {
        }

        public Integer getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(Integer paymentId) {
            this.paymentId = paymentId;
        }

        public Integer getBookingId() {
            return bookingId;
        }

        public void setBookingId(Integer bookingId) {
            this.bookingId = bookingId;
        }

        public Float getAmount() {
            return amount;
        }

        public void setAmount(Float amount) {
            this.amount = amount;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
