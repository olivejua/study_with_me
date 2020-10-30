package com.ksk.project.study_with_me.domain;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestion;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestionRepository;
import com.ksk.project.study_with_me.domain.comment.Comment;
import com.ksk.project.study_with_me.domain.comment.CommentRepository;
import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardQuestionRepository questionRepository;

    @Autowired
    CommentRepository commentRepository;

    @Before
    public void setup() {
        userRepository.save(User.builder()
                .name("olivejoa")
                .email("olivejoa@github.com")
                .role(Role.USER)
                .nickname("olivejoa")
                .socialCode("google")
                .build());

        questionRepository.save(BoardQuestion.builder()
                .title("테스트 제목")
                .content("테스트 본문")
                .viewCount(0)
                .commentCount(0)
                .user(userRepository.findAll().get(0))
                .build());
    }

    @After
    public void cleanup() {
        commentRepository.deleteAll();
        questionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 댓글등록_불러오기() {
        //given
        String boardName = MatchNames.Boards.BOARD_QUESTION.getShortName();
        Long postNo = questionRepository.findAll().get(0).getPostNo();
        User user = userRepository.findAll().get(0);
        String content = "질문게시판 댓글";

        commentRepository.save(Comment.builder()
                .boardName(boardName)
                .postNo(postNo)
                .user(user)
                .content(content)
                .build());

        //when
        List<Comment> commentList = commentRepository.findAll();

        //then
        Comment comment = commentList.get(0);
        assertThat(comment.getBoardName()).isEqualTo(boardName);
        assertThat(comment.getPostNo()).isEqualTo(postNo);
        assertThat(comment.getUser().getUserCode()).isEqualTo(user.getUserCode());
        assertThat(comment.getContent()).isEqualTo(content);
    }
}
