package vn.ptit.moviebooking.users.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import vn.ptit.moviebooking.users.dto.request.VerifyCustomerRequest;
import vn.ptit.moviebooking.users.exception.BaseBadRequestException;
import vn.ptit.moviebooking.users.entity.User;
import vn.ptit.moviebooking.users.repository.UserRepository;

import java.util.Optional;

@Service
@Validated
public class UserService {

    private final UserRepository userRepository;
    private static final String ENTITY_NAME = "UserService";

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(Integer customerId) {
        Optional<User> customerOptional = userRepository.findById(customerId);

        if (customerOptional.isEmpty()) {
            throw new BaseBadRequestException(ENTITY_NAME, "Customer not exists");
        }

        return customerOptional.get();
    }

    @Transactional
    public User saveCustomerInfo(@Valid VerifyCustomerRequest request) {
        User customer = new User();
        customer.setFullname(request.getFullname());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        return userRepository.save(customer);
    }
}
