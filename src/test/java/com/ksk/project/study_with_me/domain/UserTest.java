package com.ksk.project.study_with_me.domain;

import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    UserRepository userRepository;

    @After
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void 사용자저장_불러오기() {
        String email = "olivejoa@github.com";
        String name = "김슬기";
        Role role = Role.USER;
        String nickname = "olivejoa";
        String socialCode = "github";

        userRepository.save(User.builder()
                .email(email)
                .name(name)
                .role(role)
                .nickname(nickname)
                .socialCode(socialCode)
                .build());

        //when
        List<User> userList = userRepository.findAll();

        //then
        User user = userList.get(0);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getRole().getKey()).isEqualTo(role.getKey());
        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getSocialCode()).isEqualTo(socialCode);
    }
}
