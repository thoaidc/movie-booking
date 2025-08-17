package vn.ptit.moviebooking.booking.dto;

public interface OrderDTO {

    Integer getId();
    Integer getShowId();
    String getOrderTime();
    Float getTotal();
    String getStatus();
}
