package com.pilleasychat.project.domain.signup;

import com.pilleasychat.project.domain.entity.User;

public interface SignupService {
    void register(User user);
    User createUser(String email, String name);
    User findByEmail(String email);
}
