package vn.ptit.moviebooking.booking.dto.request;

import java.util.List;

public class BookingRequest {

    private Integer showId;
    private Integer userId;
    private List<Integer> seatIds;
    private Float totalAmount;

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
    }

    public List<Integer> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<Integer> seatIds) {
        this.seatIds = seatIds;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
