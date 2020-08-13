package com.ksk.project.study_with_me.domain.user;

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
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @After
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void User저장_불러오기() {
        //given
        String userName = "김슬기";
        String userNickname = "olivejoa";

        userRepository.save(User.builder()
                .userName(userName)
                .userNickname(userNickname)
                .build());

        //when
        List<User> userList = userRepository.findAll();

        //then
        User user = userList.get(0);
        assertThat(user.getUserName()).isEqualTo(userName);
        assertThat(user.getUserNickname()).isEqualTo(userNickname);
    }
}
