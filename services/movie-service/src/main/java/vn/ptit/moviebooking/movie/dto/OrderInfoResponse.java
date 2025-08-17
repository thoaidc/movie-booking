package vn.ptit.moviebooking.movie.dto;

import java.util.List;

public class OrderInfoResponse {

    private String movie;
    private List<String> seats;

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public List<String> getSeats() {
        return seats;
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }
}
