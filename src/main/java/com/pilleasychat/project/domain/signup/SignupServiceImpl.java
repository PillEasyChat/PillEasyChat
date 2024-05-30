package com.pilleasychat.project.domain.signup;

import com.pilleasychat.project.domain.dto.UserDto;
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
    public void register(UserDto userdto) {
        User user = dtoToEntity(userdto);
        userRepository.save(user);
    }

    @Override
    public User createUser(String email, String name) {
        User user = User.builder()
                .name(name)
                .email(email)
                .password("password")
                .build();
        userRepository.save(user);

        return user;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User dtoToEntity(UserDto userdto) {
        User user = User.builder()
                .email(userdto.getEmail())
                .password(userdto.getPassword())
                .name(userdto.getName())
                .age(userdto.getUserAge())
                .nickname(userdto.getNickname())
                .allergy(userdto.getAllergy())
                .specialNote(userdto.getSpecialNote())
                .takingMedication(userdto.getTakingMedication())
                .build();
        return user;
    }

    @Override
    public UserDto entityToDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .userAge(user.getAge())
                .nickname(user.getNickname())
                .allergy(user.getAllergy())
                .specialNote(user.getSpecialNote())
                .takingMedication(user.getTakingMedication())
                .build();
    }

    @Override
    public void update(User user, UserDto userDto) {
        user.setName(userDto.getName());
        user.setAge(userDto.getUserAge());
        user.setAllergy(userDto.getAllergy());
        user.setNickname(userDto.getNickname());
        user.setSpecialNote(userDto.getSpecialNote());
        user.setTakingMedication(userDto.getTakingMedication());
        userRepository.save(user);
    }
}
