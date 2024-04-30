package com.pilleasychat.project.domain.signup;

import com.pilleasychat.project.domain.dto.UserDto;
import com.pilleasychat.project.domain.entity.User;

public interface SignupService {
    void register(UserDto userdto);
    User createUser(String email, String name);
    User findByEmail(String email);
    User dtoToEntity(UserDto user);
}
