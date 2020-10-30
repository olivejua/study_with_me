package com.ksk.project.study_with_me.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitment;
import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitmentRepository;
import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
import com.ksk.project.study_with_me.web.dto.study.PostsSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.study.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
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
    private BoardStudyRecruitmentRepository studyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private MockHttpSession httpSession;

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
    public void tearDown() throws Exception {
        studyRepository.deleteAll();
        httpSession.clearAttributes();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 스터디모집_Posts_등록된다() throws Exception {
        //given
        String title = "스터디 모집합니다.";
        List<String> conditionLanguages = new ArrayList<>();
        conditionLanguages.add("JAVA");
        conditionLanguages.add("Spring boot");
        String conditionPlace = "강남구";
        int conditionCapacity = 5;
        Date conditionStartDate = new Date();
        Date conditionEndDate = new Date();
        String conditionExplanation = "같이 공부할 분 찾습니다.";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .conditionLanguages(conditionLanguages)
                .conditionPlace(conditionPlace)
                .conditionStartDate(conditionStartDate)
                .conditionEndDate(conditionEndDate)
                .conditionCapacity(conditionCapacity)
                .conditionExplanation(conditionExplanation)
                .build();

        String url = "http://localhost:" + port + "/board/study/posts";

        //when
         mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto))
                .session(httpSession))
                .andExpect(status().isOk());

        //then
//        List<BoardStudyRecruitment> all = studyRepository.findAll();
//        assertThat(all.get(0).getTitle()).isEqualTo(title);
//        assertThat(all.get(0).getConditionLanguages()).isEqualTo(conditionLanguages);
//        assertThat(all.get(0).getConditionPlace()).isEqualTo(conditionPlace);
//        assertThat(all.get(0).getConditionCapacity()).isEqualTo(conditionCapacity);
//        assertThat(all.get(0).getConditionStartDate()).isEqualTo(conditionStartDate);
//        assertThat(all.get(0).getConditionEndDate()).isEqualTo(conditionEndDate);
//        assertThat(all.get(0).getConditionExplanation()).isEqualTo(conditionExplanation);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void posts_수정된다() throws Exception {
        //given
        List<String> languages = new ArrayList<>();
        languages.add("java");
        languages.add("spring boot");

        BoardStudyRecruitment savedPost = studyRepository.save(PostsSaveRequestDto.builder()
                .user(userRepository.findAll().get(0))
                .title("title")
                .conditionLanguages(languages)
                .conditionPlace("강남대로")
                .conditionCapacity(5)
                .conditionStartDate(new Date())
                .conditionEndDate(new Date())
                .conditionExplanation("explanation")
                .build().toEntity());

        Long postNo = savedPost.getPostNo();

        String expectedTitle = "title2";
        String expectedExplanation = "explanation2";

        PostsUpdateRequestDto updateDto = PostsUpdateRequestDto.builder()
                .userCode(userRepository.findAll().get(0).getUserCode())
                .title(expectedTitle)
                .conditionLanguages(Arrays.asList(savedPost.getConditionLanguages().replace("[","").replace("]","").split(",")))
                .conditionPlace(savedPost.getConditionPlace())
                .conditionCapacity(savedPost.getConditionCapacity())
                .conditionStartDate(savedPost.getConditionStartDate())
                .conditionEndDate(savedPost.getConditionEndDate())
                .conditionExplanation(expectedExplanation)
                .build();

        String url = "http://localhost:" + port + "/board/study/posts/" + postNo;

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(updateDto)))
                .andExpect(status().isOk());

        //then
//        List<BoardStudyRecruitment> all = studyRepository.findAll();
//        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
//        assertThat(all.get(0).getConditionExplanation()).isEqualTo(expectedExplanation);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void posts_삭제된다() throws Exception {
        //given
        List<String> languages = new ArrayList<>();
        languages.add("java");
        languages.add("spring boot");

        BoardStudyRecruitment savedPost = studyRepository.save(BoardStudyRecruitment.builder()
                .user(userRepository.findAll().get(0))
                .title("title")
                .conditionLanguages(languages.toString())
                .conditionPlace("강남대로")
                .conditionCapacity(5)
                .conditionStartDate(new Date())
                .conditionEndDate(new Date())
                .conditionExplanation("explanation")
                .commentCount(0)
                .viewCount(0)
                .build());

        Long postNo = savedPost.getPostNo();

        String url = "http://localhost:" + port + "/board/study/posts/" + postNo;

        //when
        mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        //then
        List<BoardStudyRecruitment> all = studyRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }
}
