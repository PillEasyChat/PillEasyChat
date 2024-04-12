package com.pilleasychat.project.domain.login;

import com.pilleasychat.project.domain.entity.User;

public interface LoginService {
    User login(String email, String password);
}
