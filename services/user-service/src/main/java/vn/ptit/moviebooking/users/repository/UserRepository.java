package vn.ptit.moviebooking.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.ptit.moviebooking.users.dto.response.UserDTO;
import vn.ptit.moviebooking.users.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    @Query(value = "select u.id, u.username, u.email, u.phone from `mb_user`.`users` u where u.id = ?1", nativeQuery = true)
    Optional<UserDTO> findByUserId(Integer userId);
}
