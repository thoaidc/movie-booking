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
        private String transactionId;
        private Integer paymentId;
        private Float totalAmount;
        private String status;

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public Float getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Float totalAmount) {
            this.totalAmount = totalAmount;
        }

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
        private Float totalAmount;

        public Float getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Float totalAmount) {
            this.totalAmount = totalAmount;
        }

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
        private Float totalAmount;

        public Float getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Float totalAmount) {
            this.totalAmount = totalAmount;
        }

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
        private Float totalAmount;

        public Float getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Float totalAmount) {
            this.totalAmount = totalAmount;
        }

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
        private Float totalAmount;

        public Float getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Float totalAmount) {
            this.totalAmount = totalAmount;
        }

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
        private Float totalAmount;

        public Float getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Float totalAmount) {
            this.totalAmount = totalAmount;
        }

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
        private String transactionId;
        private Integer paymentId;
        private Integer bookingId;
        private Float amount;
        private List<Integer> seatIds;

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

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
        private String transactionId;
        private Integer paymentId;
        private Integer bookingId;
        private Float amount;
        private List<Integer> seatIds;

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

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
        private String transactionId;
        private Integer paymentId;
        private Integer bookingId;
        private Float amount;
        private boolean success;
        private List<Integer> seatIds;

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

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
        private String transactionId;
        private Integer paymentId;
        private Integer bookingId;
        private Float amount;
        private String reason;
        private List<Integer> seatIds;

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

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
        private String transactionId;
        private Integer paymentId;
        private Integer bookingId;
        private Float amount;
        private String reason;
        private List<Integer> seatIds;

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

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
