package com.pilleasychat.project.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String nickname;
    @Column
    private Long age;
    @Column
    private String specialNote;
    @Column
    private String allergy;
    @Column
    private String takingMedication;


}
