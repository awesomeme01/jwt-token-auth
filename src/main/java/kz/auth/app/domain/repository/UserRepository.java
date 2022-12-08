package kz.auth.app.domain.repository;

import kz.auth.app.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //Query is not needed
    Optional<User> findUserByUsername(String username);

}
