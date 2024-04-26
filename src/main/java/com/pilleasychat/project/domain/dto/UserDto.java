package com.pilleasychat.project.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {
    private String email;
    private String name;
    private String nickname;
    private String specialNote;
    private String allergy;
    private String takingMedication;
    private int userAge;

    @Builder
    public UserDto(String name, String nickname, String specialNote, String allergy, String takingMedication, int userAge) {
        this.name = name;
        this.nickname = nickname;
        this.specialNote = specialNote;
        this.allergy = allergy;
        this.takingMedication = takingMedication;
        this.userAge = userAge;
    }

    public
}
