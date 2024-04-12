package com.pilleasychat.project.domain.user;

import com.pilleasychat.project.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    default Optional<User> findByEmail(String email){
        return findAll().stream()
                .filter(m -> m.getEmail().equals(email))
                .findFirst();
    }
}
