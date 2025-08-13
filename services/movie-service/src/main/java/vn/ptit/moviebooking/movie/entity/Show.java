package vn.ptit.moviebooking.movie.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.Instant;
import java.time.ZonedDateTime;

@Entity
@Table(name = "show_time")
@DynamicInsert
@DynamicUpdate
@SuppressWarnings({"unused", "JpaDataSourceORMInspection"})
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "movie_id", nullable = false)
    private Integer movieId;

    @Column(name = "cinema_room_id", nullable = false)
    private Integer cinemaRoomId;

    @Column(name = "ticket_price", nullable = false)
    private Float ticketPrice;

    @Column(name = "start_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private ZonedDateTime startTime;

    @Column(name = "end_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private ZonedDateTime endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getCinemaRoomId() {
        return cinemaRoomId;
    }

    public void setCinemaRoomId(Integer cinemaRoomId) {
        this.cinemaRoomId = cinemaRoomId;
    }

    public Float getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }
}
