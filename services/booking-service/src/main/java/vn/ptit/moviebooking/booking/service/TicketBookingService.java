package vn.ptit.moviebooking.booking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.ptit.moviebooking.booking.constants.BookingConstants;
import vn.ptit.moviebooking.booking.dto.OrderDTO;
import vn.ptit.moviebooking.booking.dto.OrderResponse;
import vn.ptit.moviebooking.booking.dto.request.BookingRequest;
import vn.ptit.moviebooking.booking.dto.request.GetOrderInfoRequest;
import vn.ptit.moviebooking.booking.dto.request.NotificationRequest;
import vn.ptit.moviebooking.booking.dto.response.BaseResponseDTO;
import vn.ptit.moviebooking.booking.dto.response.OrderInfoResponse;
import vn.ptit.moviebooking.booking.dto.response.UserDTO;
import vn.ptit.moviebooking.booking.entity.Booking;
import vn.ptit.moviebooking.booking.entity.BookingSeat;
import vn.ptit.moviebooking.booking.exception.BaseBadRequestException;
import vn.ptit.moviebooking.booking.repository.BookingSeatRepository;
import vn.ptit.moviebooking.booking.repository.TicketBookingRepository;
import vn.ptit.moviebooking.booking.service.api.MovieClient;
import vn.ptit.moviebooking.booking.service.api.UserServiceClient;
import vn.ptit.moviebooking.common.Event;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TicketBookingService {

    private final TicketBookingRepository ticketBookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final UserServiceClient userServiceClient;
    private final MovieClient movieClient;
    private final ObjectMapper objectMapper;
    private static final String ENTITY_NAME = "TicketBookingService";
    private static final Logger log = LoggerFactory.getLogger(TicketBookingService.class);

    public TicketBookingService(TicketBookingRepository ticketBookingRepository,
                                BookingSeatRepository bookingSeatRepository,
                                UserServiceClient userServiceClient, MovieClient movieClient,
                                ObjectMapper objectMapper) {
        this.ticketBookingRepository = ticketBookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.userServiceClient = userServiceClient;
        this.movieClient = movieClient;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public Booking createBooking(BookingRequest bookingRequest) {
        Booking booking = new Booking();
        booking.setShowId(bookingRequest.getShowId());
        booking.setUserId(bookingRequest.getUserId());
        booking.setTotalAmount(bookingRequest.getTotalAmount());
        booking.setStatus(BookingConstants.Status.PENDING);
        booking.setCreateTime(ZonedDateTime.now(ZoneId.systemDefault()));
        ticketBookingRepository.save(booking);

        List<BookingSeat> bookingSeats = new ArrayList<>();

        for (Integer seatId : bookingRequest.getSeatIds()) {
            BookingSeat bookingSeat = new BookingSeat();
            bookingSeat.setBookingId(booking.getId());
            bookingSeat.setSeatId(seatId);
            bookingSeats.add(bookingSeat);
        }

        bookingSeatRepository.saveAll(bookingSeats);
        return booking;
    }

    @Transactional
    public void updateBookingStatus(Integer bookingId, String status) {
        Optional<Booking> bookingOptional = ticketBookingRepository.findById(bookingId);

        if (bookingOptional.isEmpty()) {
            throw new BaseBadRequestException(ENTITY_NAME, "Booking not exists, cannot update status");
        }

        Booking booking = bookingOptional.get();
        booking.setStatus(status);
        ticketBookingRepository.save(booking);
    }

    public NotificationRequest createNotification(Event.MarkBookingSuccessEvent event) {
        BaseResponseDTO responseDTO = userServiceClient.getUserInfo(event.getUserId());

        if (responseDTO.getStatus() && Objects.nonNull(responseDTO.getResult())) {
            try {
                UserDTO userDTO = objectMapper.convertValue(responseDTO.getResult(), UserDTO.class);
                NotificationRequest notificationRequest = new NotificationRequest();
                notificationRequest.setSender("MOVIE-BOOKING-SYSTEM");
                notificationRequest.setReceiver(userDTO.getEmail());
                notificationRequest.setTitle("Your ticket booking has completed successfully!");
                notificationRequest.setContent("Please double check the information on your ticket and make sure it is correct.");
                return notificationRequest;
            } catch (Exception e) {
                log.error("Could not create email: {}", e.getMessage());
            }
        }

        return null;
    }

    public BaseResponseDTO getOrders(Integer userId) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        List<OrderDTO> orderDTOS = ticketBookingRepository.getAllOrders(userId);

        if (Objects.nonNull(orderDTOS) && !orderDTOS.isEmpty()) {
            for (OrderDTO orderDTO : orderDTOS) {
                Integer showId = orderDTO.getShowId();
                List<Integer> seatIds = bookingSeatRepository.findAllSeatIdsByBookingId(orderDTO.getId());
                GetOrderInfoRequest request = new GetOrderInfoRequest();
                request.setShowId(showId);
                request.setSeatIds(seatIds);
                BaseResponseDTO responseDTO = movieClient.getMovieInfo(request);
                OrderInfoResponse orderInfoResponse = objectMapper.convertValue(responseDTO.getResult(), OrderInfoResponse.class);
                BaseResponseDTO response = userServiceClient.getUserInfo(userId);
                UserDTO userDTO = objectMapper.convertValue(response.getResult(), UserDTO.class);
                OrderResponse orderResponse = new OrderResponse();
                orderResponse.setId(orderDTO.getId());
                orderResponse.setDate(orderDTO.getOrderTime());
                orderResponse.setMovie(orderInfoResponse.getMovie());
                orderResponse.setSeats(orderInfoResponse.getSeats());
                orderResponse.setStatus(orderDTO.getStatus());
                orderResponse.setCustomer(userDTO.getUsername());
                orderResponse.setTotal(orderDTO.getTotal());
                orderResponses.add(orderResponse);
            }
        }

        return BaseResponseDTO.builder().ok(orderResponses);
    }
}
