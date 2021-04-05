package com.ksk.project.study_with_me.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestion;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestionRepository;
import com.ksk.project.study_with_me.domain.comment.Comment;
import com.ksk.project.study_with_me.domain.comment.CommentRepository;
import com.ksk.project.study_with_me.domain.reply.Reply;
import com.ksk.project.study_with_me.domain.reply.ReplyRepository;
import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
import com.ksk.project.study_with_me.web.dto.reply.ReplySaveRequestDto;
import com.ksk.project.study_with_me.web.dto.reply.ReplyUpdateRequestDto;
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
public class ReplyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ReplyRepository replyRepository;

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

        commentRepository.save(Comment.builder()
                .boardName(MatchNames.Boards.BOARD_QUESTION.getShortName())
                .postNo(questionRepository.findAll().get(0).getPostNo())
                .user(userRepository.findAll().get(0))
                .content("댓글 내용")
                .build());

        User sampleUser = userRepository.findAll().get(0);
        httpSession = new MockHttpSession();
        httpSession.setAttribute("user", new SessionUser(sampleUser));
    }

    @After
    public void tearDown() throws Exception {
        replyRepository.deleteAll();
        commentRepository.deleteAll();
        questionRepository.deleteAll();
        httpSession.clearAttributes();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void reply_등록() throws Exception {
        //given
        Long commentNo = commentRepository.findAll().get(0).getCommentNo();
        String content = "답글 내용";
        Long postNo = questionRepository.findAll().get(0).getPostNo();
        String boardName = MatchNames.Boards.BOARD_QUESTION.getShortName();

        ReplySaveRequestDto requestDto = ReplySaveRequestDto.builder()
                .commentNo(commentNo)
                .content(content)
                .postNo(postNo)
                .boardName(boardName)
                .build();

        String url = "http://localhost:" + port + "/board/posts/reply";

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto))
                .session(httpSession))
                .andExpect(status().isOk());

        List<Reply> all = replyRepository.findAll();
        assertThat(all.get(0).getCommentNo()).isEqualTo(commentNo);
        assertThat(all.get(0).getPostNo()).isEqualTo(postNo);
        assertThat(all.get(0).getBoardName()).isEqualTo(boardName);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void reply_수정() throws Exception {
        //given
        Reply savedReply = replyRepository.save(ReplySaveRequestDto.builder()
                .commentNo(commentRepository.findAll().get(0).getCommentNo())
                .content("답글 내용")
                .postNo(questionRepository.findAll().get(0).getPostNo())
                .boardName(MatchNames.Boards.BOARD_QUESTION.getShortName())
                .build().toEntity());

        String expectedContent = "답글 내용 수정";

        ReplyUpdateRequestDto updateDto = ReplyUpdateRequestDto.builder()
                .replyNo(savedReply.getReplyNo())
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/board/posts/reply/" + savedReply.getReplyNo();

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(updateDto)))
                .andExpect(status().isOk());

        Reply entity = replyRepository.findAll().get(0);
        assertThat(entity.getReplyNo()).isEqualTo(savedReply.getReplyNo());
        assertThat(entity.getContent()).isEqualTo(expectedContent);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void reply_삭제() throws Exception {
        //given
        Reply savedReply = replyRepository.save(ReplySaveRequestDto.builder()
                .commentNo(commentRepository.findAll().get(0).getCommentNo())
                .content("답글 내용")
                .postNo(questionRepository.findAll().get(0).getPostNo())
                .boardName(MatchNames.Boards.BOARD_QUESTION.getShortName())
                .build().toEntity());

        String url = "http://localhost:" + port + "/board/posts/reply/" + savedReply.getReplyNo();

        //when
        mvc.perform(delete(url))
                .andExpect(status().isOk());

        //then
        List<Reply> all = replyRepository.findAll();
        assertThat(all).hasSize(0);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void replies_가져온다() throws Exception {
        //given
        Reply savedReply = replyRepository.save(ReplySaveRequestDto.builder()
                .commentNo(commentRepository.findAll().get(0).getCommentNo())
                .content("답글 내용")
                .postNo(questionRepository.findAll().get(0).getPostNo())
                .boardName(MatchNames.Boards.BOARD_QUESTION.getShortName())
                .build().toEntity());

        String url = "http://localhost:" + port + "/board/posts/reply/list";

        mvc.perform(get(url)
                .param("boardName", String.valueOf(savedReply.getBoardName()))
                .param("postNo", String.valueOf(savedReply.getPostNo())))
                .andExpect(status().isOk());
    }
}
