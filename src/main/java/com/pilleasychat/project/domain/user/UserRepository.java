package com.pilleasychat.project.domain.user;

import com.pilleasychat.project.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    default Optional<User> findByEmail(String email) {
        if (email == null) {
            return Optional.empty(); // 이메일이 null인 경우, 빈 Optional을 반환합니다.
        }

        return findAll().stream()
                .filter(m -> {
                    String userEmail = m.getEmail();
                    return userEmail != null && email.equals(userEmail);
                })
                .findFirst();
    }
}
