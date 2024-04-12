package com.pilleasychat.project.domain.user;

import com.pilleasychat.project.domain.entity.User;


public interface UserService {
    void save(User user);
    User findById(Long id);
    User findByEmail(String email);
    void deleteById(Long id);
    void update(User user);
}
