package com.ksk.project.study_with_me.domain;

import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    public void 사용자_생성() {
        //given
        String email = "olivejoa@github.com";
        String name = "김슬기";
        Role role = Role.USER;
        String nickname = "olivejoa";
        String socialCode = "github";

        //when
        User user = User.builder()
                .name(name)
                .email(email)
                .role(role)
                .nickname(nickname)
                .socialCode(socialCode)
                .build();

        //then
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getRole().getKey()).isEqualTo(role.getKey());
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getSocialCode()).isEqualTo(socialCode);
    }
}
