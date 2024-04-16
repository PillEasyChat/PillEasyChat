package com.pilleasychat.project.domain.signup;

import com.pilleasychat.project.domain.entity.User;
import com.pilleasychat.project.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignupServiceImpl implements SignupService {

    private final UserRepository userRepository;
    @Override
    public void register(User user) {
        userRepository.save(user);
    }

    @Override
    public User createUser(String email, String name) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        //패스워드 암호화
        user.setPassword("password");
        userRepository.save(user);

        return user;
    }
}
