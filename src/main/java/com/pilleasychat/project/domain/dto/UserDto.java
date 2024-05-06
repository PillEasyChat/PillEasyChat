package com.pilleasychat.project.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String email;
    private String name;
    private String password;
    private String nickname;
    private String specialNote;
    private String allergy;
    private String takingMedication;
    private Long userAge;

    @Builder
    public UserDto(String email, String name, String password, String nickname, String specialNote, String allergy, String takingMedication, Long userAge) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.specialNote = specialNote;
        this.allergy = allergy;
        this.takingMedication = takingMedication;
        this.userAge = userAge;
    }
}
