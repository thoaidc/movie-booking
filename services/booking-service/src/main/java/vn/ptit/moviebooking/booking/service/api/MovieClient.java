package vn.ptit.moviebooking.booking.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import vn.ptit.moviebooking.booking.dto.request.GetOrderInfoRequest;
import vn.ptit.moviebooking.booking.dto.response.BaseResponseDTO;

@FeignClient(name = "movie-service")
public interface MovieClient {

    @PostMapping("/api/p/movies/info")
    BaseResponseDTO getMovieInfo(GetOrderInfoRequest request);
}
