package vn.ptit.moviebooking.common;

import java.util.List;

public class Event {

    // Booking
    public static class CreateBookingEvent {
        private Integer bookingId;
        private Float totalAmount;
        private List<Integer> seatIds;

        public CreateBookingEvent() {}

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

    public static class MarkBookingSuccessEvent {
        private Integer bookingId;

        public MarkBookingSuccessEvent() {
        }

        public Integer getBookingId() {
            return bookingId;
        }

        public void setBookingId(Integer bookingId) {
            this.bookingId = bookingId;
        }
    }

    public static class MarkBookingFailedEvent {
        private Integer bookingId;
        private Integer paymentId;
        private String transactionId;
        private String reason;
        private String status;
        private Float totalAmount;

        public Float getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Float totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
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

        public MarkBookingFailedEvent() {
        }

        public Integer getBookingId() {
            return bookingId;
        }

        public void setBookingId(Integer bookingId) {
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
    public static class CreateCheckSeatEvent {
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

        public CreateCheckSeatEvent() {
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

    public static class ReserveSeatResultEvent {
        private String seatReservationId;
        private Integer bookingId;
        private Float amount;
        private List<Integer> seatIds;
        private boolean success;

        public ReserveSeatResultEvent() {
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
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

    public static class CancelSeatResultEvent {
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

        public CancelSeatResultEvent() {
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
    public static class CreatePaymentEvent {
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

        public CreatePaymentEvent() {
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

    public static class PaymentResultEvent {
        private String transactionId;
        private Integer paymentId;
        private Integer bookingId;
        private Float amount;
        private boolean success;
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

        public PaymentResultEvent() {
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
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

    public static class RefundResultEvent {
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

        public RefundResultEvent() {
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
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
}
