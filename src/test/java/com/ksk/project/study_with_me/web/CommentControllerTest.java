package com.ksk.project.study_with_me.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestion;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestionRepository;
import com.ksk.project.study_with_me.domain.comment.Comment;
import com.ksk.project.study_with_me.domain.comment.CommentRepository;
import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
import com.ksk.project.study_with_me.web.dto.comment.CommentSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.comment.CommentUpdateRequestDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CommentRepository commentRepository;

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

        questionRepository.save(BoardQuestion.builder()
                .title("질문 게시글 제목")
                .content("질문 게시글 내용")
                .viewCount(0)
                .commentCount(0)
                .user(userRepository.findAll().get(0))
                .build());


        User sampleUser = userRepository.findAll().get(0);
        httpSession = new MockHttpSession();
        httpSession.setAttribute("user", new SessionUser(sampleUser));
    }

    @After
    public void tearDown() throws Exception {
        commentRepository.deleteAll();
        questionRepository.deleteAll();
        httpSession.clearAttributes();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void comment_등록() throws Exception {
        //given
        String boardName = MatchNames.Boards.BOARD_QUESTION.getShortName();
        Long postNo = questionRepository.findAll().get(0).getPostNo();
        Long userCode = userRepository.findAll().get(0).getUserCode();
        String content = "댓글 내용";

        CommentSaveRequestDto requestDto = CommentSaveRequestDto.builder()
                .boardName(boardName)
                .postNo(postNo)
                .userCode(userCode)
                .content(content)
                .build();

        String url = "http://localhost:" + port + "/board/posts/comment";

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Comment> all = commentRepository.findAll();
        assertThat(all.get(0).getBoardName()).isEqualTo(boardName);
        assertThat(all.get(0).getUser().getUserCode()).isEqualTo(userCode);
        assertThat(all.get(0).getPostNo()).isEqualTo(postNo);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void comment_수정() throws Exception {
        //given
        Comment savedComment = commentRepository.save(CommentSaveRequestDto.builder()
                .boardName(MatchNames.Boards.BOARD_QUESTION.getShortName())
                .postNo(questionRepository.findAll().get(0).getPostNo())
                .userCode(userRepository.findAll().get(0).getUserCode())
                .content("댓글 내용")
                .build().toEntity());

        String expectedContent = "댓글 내용 수정";

        CommentUpdateRequestDto updateDto = CommentUpdateRequestDto.builder()
                .commentNo(savedComment.getCommentNo())
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/board/posts/comment/" + savedComment.getCommentNo();

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(updateDto)))
                .andExpect(status().isOk());

        //then
        List<Comment> all = commentRepository.findAll();
        assertThat(all.get(0).getCommentNo()).isEqualTo(savedComment.getCommentNo());
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void comment_삭제() throws Exception {
        //given
        Comment savedComment = commentRepository.save(CommentSaveRequestDto.builder()
                .boardName(MatchNames.Boards.BOARD_QUESTION.getShortName())
                .postNo(questionRepository.findAll().get(0).getPostNo())
                .userCode(userRepository.findAll().get(0).getUserCode())
                .content("댓글 내용")
                .build().toEntity());

        String url = "http://localhost:" + port + "/board/posts/comment/" + savedComment.getCommentNo();

        mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        //then
        List<Comment> all = commentRepository.findAll();
        assertThat(all).hasSize(0);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void comments_가져온다() throws Exception {
        //given
        Comment savedComment = commentRepository.save(CommentSaveRequestDto.builder()
                .boardName(MatchNames.Boards.BOARD_QUESTION.getShortName())
                .postNo(questionRepository.findAll().get(0).getPostNo())
                .userCode(userRepository.findAll().get(0).getUserCode())
                .content("댓글 내용")
                .build().toEntity());

        String url = "http://localhost:" + port + "/board/posts/comment/list";

        mvc.perform(get(url)
                .param("boardName", String.valueOf(savedComment.getBoardName()))
                .param("postNo", String.valueOf(savedComment.getPostNo())))
                .andExpect(status().isOk());
    }
}
