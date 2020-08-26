package com.ksk.project.study_with_me.web.dto;

import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Getter;

@Getter
public class UserSignupRequestDto {
    private String name;
    private String email;
    private String role;
    private String socialCode;
    private String nickname;

    public UserSignupRequestDto(String name, String email, String role, String socialCode, String nickname) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.socialCode = socialCode;
        this.nickname = nickname;
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .nickname(nickname)
                .socialCode(socialCode)
                .build();
    }
}
