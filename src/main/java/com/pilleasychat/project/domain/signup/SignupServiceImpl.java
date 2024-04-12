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
}
