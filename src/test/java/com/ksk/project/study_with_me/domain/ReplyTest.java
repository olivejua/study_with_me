package com.ksk.project.study_with_me.domain;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestion;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestionRepository;
import com.ksk.project.study_with_me.domain.comment.Comment;
import com.ksk.project.study_with_me.domain.comment.CommentRepository;
import com.ksk.project.study_with_me.domain.reply.Reply;
import com.ksk.project.study_with_me.domain.reply.ReplyRepository;
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
public class ReplyTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardQuestionRepository questionRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ReplyRepository replyRepository;

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

        commentRepository.save(Comment.builder()
                .boardName(MatchNames.Boards.BOARD_QUESTION.getShortName())
                .postNo(questionRepository.findAll().get(0).getPostNo())
                .user(userRepository.findAll().get(0))
                .content("게시글 댓글")
                .build());
    }

    @After
    public void cleanup() {
        replyRepository.deleteAll();
        commentRepository.deleteAll();
        questionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 답글등록_불러오기() {
        //given
        User user = userRepository.findAll().get(0);
        Long postNo = questionRepository.findAll().get(0).getPostNo();
        Long commentNo = commentRepository.findAll().get(0).getCommentNo();
        String boardName = MatchNames.Boards.BOARD_QUESTION.getShortName();
        String content = "댓글의 답글";

        replyRepository.save(Reply.builder()
                .boardName(boardName)
                .postNo(postNo)
                .commentNo(commentNo)
                .user(user)
                .content(content)
                .build());

        List<Reply> replyList = replyRepository.findAll();

        Reply reply = replyList.get(0);

        assertThat(reply.getBoardName()).isEqualTo(boardName);
        assertThat(reply.getPostNo()).isEqualTo(postNo);
        assertThat(reply.getCommentNo()).isEqualTo(commentNo);
        assertThat(reply.getUser().getUserCode()).isEqualTo(user.getUserCode());
        assertThat(reply.getContent()).isEqualTo(content);
    }
}
