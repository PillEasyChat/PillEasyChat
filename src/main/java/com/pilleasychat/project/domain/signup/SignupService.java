package com.pilleasychat.project.domain.signup;

import com.pilleasychat.project.domain.dto.UserDto;
import com.pilleasychat.project.domain.entity.User;

public interface SignupService {
    void register(UserDto userdto);
    void generalRegister(UserDto userdto);
    User createUser(String email, String name);
    User findByEmail(String email);
    User dtoToEntity(UserDto user);
    UserDto entityToDto(User user);
    void update(User user, UserDto userDto);
}
