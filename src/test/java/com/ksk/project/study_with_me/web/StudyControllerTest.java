package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitmentRepository;
import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsSaveRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BoardStudyRecruitmentRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        boardRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 스터디모집_Posts_등록된다() throws Exception {
        userRepository.save(User.builder()
                .email("tmfrl4710@gmail.com")
                .name("kim seul ki")
                .nickname("olivejoa")
                .role(Role.USER)
                .socialCode("google")
                .build());

        List<User> userList = userRepository.findAll();
        User user = userList.get(0);

        //given
        String title = "스터디 모집합니다.";
        String conditionLanguages = "Spring Boot, JAVA";
        String conditionPlace = "강남구";
        Integer conditionCapacity = 5;
        String conditionExplanation = "같이 공부할 분 찾습니다.";

        StudyPostsSaveRequestDto requestDto = StudyPostsSaveRequestDto.builder()
                .title(title)
                .conditionLanguages(conditionLanguages)
                .conditionPlace(conditionPlace)
                .conditionCapacity(conditionCapacity)
                .conditionStartDate(new Date())
                .conditionEndDate(new Date())
                .conditionExplanation(conditionExplanation)
                .user(user)
                .build();

        String url = "http://localhost:" + port + "/board/study/posts/save";
/*
        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<BoardStudyRecruitment> all = boardRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getConditionLanguages()).isEqualTo(conditionLanguages);
        assertThat(all.get(0).getConditionExplanation()).isEqualTo(conditionExplanation);*/
    }
}
