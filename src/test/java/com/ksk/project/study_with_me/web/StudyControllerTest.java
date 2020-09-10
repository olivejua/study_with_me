package com.ksk.project.study_with_me.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitment;
import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitmentRepository;
import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private MockHttpSession httpSession;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardStudyRecruitmentRepository boardRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        userRepository.save(User.builder()
                .name("olivejoa")
                .email("olivejoa@github.com")
                .role(Role.USER)
                .nickname("olivejoa")
                .socialCode("google")
                .build());

        User sampleUser = userRepository.findAll().get(0);


        httpSession = new MockHttpSession();

        httpSession.setAttribute("user", new SessionUser(sampleUser));
    }

    @After
    public void cleanup() {
        boardRepository.deleteAll();
        httpSession.clearAttributes();
        userRepository.delete(userRepository.findAll().get(0));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 스터디모집_Posts_등록된다() throws Exception {
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
                .build();

        String url = "http://localhost:" + port + "/board/study/posts";

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .session(httpSession)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<BoardStudyRecruitment> all = boardRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getConditionLanguages()).isEqualTo(conditionLanguages);
        assertThat(all.get(0).getConditionExplanation()).isEqualTo(conditionExplanation);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 스터디모집_Posts_수정된다() throws Exception {
        //given
        BoardStudyRecruitment savedPost = boardRepository.save(BoardStudyRecruitment.builder()
                .title("샘플제목입니다.")
                .conditionLanguages("Spring Boot, JAVA")
                .conditionPlace("강남구")
                .conditionCapacity(5)
                .conditionStartDate(new Date())
                .conditionEndDate(new Date())
                .conditionExplanation("같이 공부할 분 찾습니다.")
                .user(userRepository.findAll().get(0))
                .build());


        String expectedTitle = "수정한 샘플제목입니다.";
        int expectedCapacity = 10;

        StudyPostsUpdateRequestDto requestDto = StudyPostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .conditionLanguages(savedPost.getConditionLanguages())
                .conditionPlace(savedPost.getConditionPlace())
                .conditionCapacity(expectedCapacity)
                .conditionStartDate(savedPost.getConditionStartDate())
                .conditionEndDate(savedPost.getConditionEndDate())
                .conditionExplanation(savedPost.getConditionExplanation())
                .build();

        Long postNo = savedPost.getPostNo();

        String url = "http://localhost:" + port + "/board/study/posts/" + postNo;

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<BoardStudyRecruitment> all = boardRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getConditionCapacity()).isEqualTo(expectedCapacity);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 스터디모집_Posts_삭제된다() throws Exception {
        //given
        BoardStudyRecruitment savedPost = boardRepository.save(BoardStudyRecruitment.builder()
                .title("샘플제목입니다.")
                .conditionLanguages("Spring Boot, JAVA")
                .conditionPlace("강남구")
                .conditionCapacity(5)
                .conditionStartDate(new Date())
                .conditionEndDate(new Date())
                .conditionExplanation("같이 공부할 분 찾습니다.")
                .user(userRepository.findAll().get(0))
                .build());


        Long postNo = savedPost.getPostNo();

        String url = "http://localhost:" + port + "/board/study/posts/" + postNo;

        //when
        mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }
}
