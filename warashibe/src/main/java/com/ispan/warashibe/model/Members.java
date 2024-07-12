package com.ispan.warashibe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "members")
@Setter
@Getter
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @NotBlank
    @Size(max = 50)
    private String username;

    @NotBlank
    @Size(max = 100)
    private String password;

    @NotBlank
    @Size(max = 100)
    private String fullname;

    @NotBlank
    @Size(max = 100)
    private String nickname;

    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @Size(max = 15)
    private String phone;

    @NotNull
    private Boolean isActive;

    @NotNull
    private LocalDateTime registrationDate;

    @Size(max = 100)
    private String address;

    @Size(max = 10)
    private String postalCode;

    @Size(max = 50)
    private String city;

    @Size(max = 50)
    private String country;

    @Size(max = 100)
    private String profilePicture;

    @Size(max = 255)
    private String aboutMe;

    @NotNull
    private Boolean isAdmin;
}
