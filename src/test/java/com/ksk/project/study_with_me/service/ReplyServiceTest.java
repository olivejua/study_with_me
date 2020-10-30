package com.ksk.project.study_with_me.service;

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
import com.ksk.project.study_with_me.web.dto.reply.ReplyListResponseDto;
import com.ksk.project.study_with_me.web.dto.reply.ReplySaveRequestDto;
import com.ksk.project.study_with_me.web.dto.reply.ReplyUpdateRequestDto;
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
public class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardQuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

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
                .user(userRepository.findAll().get(0))
                .boardName(MatchNames.Boards.BOARD_QUESTION.getShortName())
                .postNo(questionRepository.findAll().get(0).getPostNo())
                .content("댓글 내용")
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
    public void reply테이블에_답글_저장하기() {
        //given
        ReplySaveRequestDto dto = ReplySaveRequestDto.builder()
                .commentNo(commentRepository.findAll().get(0).getCommentNo())
                .postNo(questionRepository.findAll().get(0).getPostNo())
                .boardName(MatchNames.Boards.BOARD_QUESTION.getShortName())
                .content("대댓글 내용")
                .build();

        //when
        replyService.save(dto.setUser(userRepository.findAll().get(0)));

        //then
        Reply reply = replyRepository.findAll().get(0);
        assertThat(reply.getUser().getUserCode()).isEqualTo(dto.getUser().getUserCode());
        assertThat(reply.getBoardName()).isEqualTo(dto.getBoardName());
        assertThat(reply.getPostNo()).isEqualTo(dto.getPostNo());
        assertThat(reply.getCommentNo()).isEqualTo(dto.getCommentNo());
        assertThat(reply.getContent()).isEqualTo(dto.getContent());
    }

    @Test
    public void reply테이블에_답글_수정하기() {
        //given
        Reply savedReply = this.save_reply_in_repository();

        ReplyUpdateRequestDto updateDto = ReplyUpdateRequestDto.builder()
                .replyNo(savedReply.getReplyNo())
                .content("대댓글 내용 수정")
                .build();

        //when
        replyService.update(savedReply.getReplyNo(), updateDto);

        //then
        Reply reply = replyRepository.findAll().get(0);
        assertThat(reply.getContent()).isEqualTo(updateDto.getContent());
    }

    @Test
    public void reply테이블에_답글_삭제하기() {
        //given
        Reply savedReply = this.save_reply_in_repository();

        //when
        replyService.delete(savedReply.getReplyNo());

        //then
        assertThat(replyRepository.existsById(savedReply.getReplyNo())).isEqualTo(false);
    }

    @Test
    public void commentNo로_replies_삭제하기() {
        //given
        int givenSize = 5;
        for(int i=0; i<givenSize; i++) {
            this.save_reply_in_repository();
        }

        Long commentNo = commentRepository.findAll().get(0).getCommentNo();

        //when
        replyService.deleteAllByCommentNo(commentNo);

        //then
        assertThat(replyRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void boardName과_postNo로_replies_가져오기() {
        //given
        int givenSize = 5;
        for(int i=0; i<givenSize; i++) {
            this.save_reply_in_repository();
        }

        //when
        Long postNo = questionRepository.findAll().get(0).getPostNo();
        String boardName = MatchNames.Boards.BOARD_QUESTION.getShortName();

        //then
        List<ReplyListResponseDto> replies =  replyService.findAllByPostNoAndBoardName(postNo, boardName);
        assertThat(replies.size()).isEqualTo(givenSize);
    }

    @Test
    public void commentNo로_replies_가져오기() {
        //given
        int givenSize = 5;
        for(int i=0; i<givenSize; i++) {
            this.save_reply_in_repository();
        }

        //when
        Long commentNo = commentRepository.findAll().get(0).getCommentNo();

        //then
        List<ReplyListResponseDto> replies = replyService.findAllByCommentNo(commentNo);
        assertThat(replies.size()).isEqualTo(givenSize);
    }

    @Test
    public void boardName과_postNo로_replies갯수_가져오기() {
        //given
        int givenSize = 5;
        for(int i=0; i<givenSize; i++) {
            this.save_reply_in_repository();
        }

        Long postNo = questionRepository.findAll().get(0).getPostNo();
        String boardName = MatchNames.Boards.BOARD_QUESTION.getShortName();

        //when
        int actualSize = replyService.countByPostNoAndBoardName(postNo, boardName);
        assertThat(actualSize).isEqualTo(givenSize);
    }

    private Reply save_reply_in_repository() {
        ReplySaveRequestDto dto = ReplySaveRequestDto.builder()
                .commentNo(commentRepository.findAll().get(0).getCommentNo())
                .postNo(questionRepository.findAll().get(0).getPostNo())
                .boardName(MatchNames.Boards.BOARD_QUESTION.getShortName())
                .content("대댓글 내용")
                .build().setUser(userRepository.findAll().get(0));

        return replyRepository.save(dto.toEntity());
    }
}
