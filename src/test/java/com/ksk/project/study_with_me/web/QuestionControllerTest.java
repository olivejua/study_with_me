package com.ksk.project.study_with_me.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestion;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestionRepository;
import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
import com.ksk.project.study_with_me.web.dto.question.PostsSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.question.PostsUpdateRequestDto;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuestionControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private BoardQuestionRepository questionRepository;

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
        questionRepository.deleteAll();
        httpSession.clearAttributes();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Posts_등록된다() throws Exception {
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .userCode(userRepository.findAll().get(0).getUserCode())
                .build();

        String url = "http://localhost:" + port + "/board/question/posts";

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<BoardQuestion> all = questionRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void posts_수정된다() throws Exception {
        //given
        BoardQuestion savedPost = questionRepository.save(PostsSaveRequestDto.builder()
                .title("title")
                .content("content")
                .userCode(userRepository.findAll().get(0).getUserCode())
                .build().toEntity());

        Long postNo = savedPost.getPostNo();
        String expectedTitle = "title 수정";
        String expectedContent = "content 수정";

        PostsUpdateRequestDto updateDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/board/question/posts/" + postNo;

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(updateDto)))
                .andExpect(status().isOk());

        //then
        List<BoardQuestion> all = questionRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void posts_삭제된다() throws Exception {
        //given
        BoardQuestion savedPost = questionRepository.save(PostsSaveRequestDto.builder()
                .title("title")
                .content("content")
                .userCode(userRepository.findAll().get(0).getUserCode())
                .build().toEntity());

        Long postNo = savedPost.getPostNo();

        String url = "http://localhost:" + port + "/board/question/posts/" + postNo;

        mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        //then
        List<BoardQuestion> all = questionRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void posts_가져온다() throws Exception {
        //given
        BoardQuestion savedPost = questionRepository.save(PostsSaveRequestDto.builder()
                .title("title")
                .content("content")
                .userCode(userRepository.findAll().get(0).getUserCode())
                .build().toEntity());

        Long postNo = savedPost.getPostNo();

        String url = "http://localhost:" + port + "/board/question/posts";

        mvc.perform(get(url)
                .param("postNo", String.valueOf(postNo))
                .param("page", String.valueOf(0))
                .session(httpSession))
                .andExpect(status().isOk())
                .andExpect(view().name("board/question/posts-read"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("post"));
    }
}
