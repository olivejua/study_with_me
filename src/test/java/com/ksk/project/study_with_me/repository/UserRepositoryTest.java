package com.ksk.project.study_with_me.repository;

import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @After
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void 사용자저장_불러오기() {
        //given



//        userRepository.save(User.builder()
//                .email("olivejoa@github.com")
//                .name("김슬기")
//                .role(Role.USER)
//                .nickname("olivejoa")
//                .socialCode("github")
//                .build());
//
//        //when
//        List<User> userList = userRepository.findAll();
//
//        //then
//        User findUser = userList.get(0);
//        assertThat(findUser.getEmail()).isEqualTo(email);
//        assertThat(findUser.getName()).isEqualTo(name);
//        assertThat(findUser.getRole().getKey()).isEqualTo(role.getKey());
//        assertThat(findUser.getNickname()).isEqualTo(nickname);
//        assertThat(findUser.getSocialCode()).isEqualTo(socialCode);
    }
}