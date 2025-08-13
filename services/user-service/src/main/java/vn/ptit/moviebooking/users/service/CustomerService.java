package vn.ptit.moviebooking.users.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import vn.ptit.moviebooking.users.dto.request.VerifyCustomerRequest;
import vn.ptit.moviebooking.users.exception.BaseBadRequestException;
import vn.ptit.moviebooking.users.repository.CustomerRepository;
import vn.ptit.moviebooking.users.entity.User;

import java.util.Optional;

@Service
@Validated
public class CustomerService {

    private final CustomerRepository customerRepository;
    private static final String ENTITY_NAME = "CustomerService";

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public User getCustomerById(Integer customerId) {
        Optional<User> customerOptional = customerRepository.findById(customerId);

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
        return customerRepository.save(customer);
    }
}
