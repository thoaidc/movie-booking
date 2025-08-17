package vn.ptit.moviebooking.movie.dto;

import java.util.List;

public class GetOrderInfoRequest {

    private Integer showId;
    private List<Integer> seatIds;

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
}
