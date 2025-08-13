package vn.ptit.moviebooking.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ptit.moviebooking.users.entity.User;

@Repository
public interface CustomerRepository extends JpaRepository<User, Integer> {}
