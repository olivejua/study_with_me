package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestion;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestionRepository;
import com.ksk.project.study_with_me.domain.comment.Comment;
import com.ksk.project.study_with_me.domain.comment.CommentRepository;
import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
import com.ksk.project.study_with_me.web.dto.comment.CommentListResponseDto;
import com.ksk.project.study_with_me.web.dto.comment.CommentSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.comment.CommentUpdateRequestDto;
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
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

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
    }

    @After
    public void cleanup() {
        commentRepository.deleteAll();
        questionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void comment테이블에_댓글_저장하기() {
        //given
        CommentSaveRequestDto dto = CommentSaveRequestDto.builder()
                .userCode(userRepository.findAll().get(0).getUserCode())
                .boardName(MatchNames.Boards.BOARD_QUESTION.getShortName())
                .postNo(questionRepository.findAll().get(0).getPostNo())
                .content("댓글 내용")
                .build();

        //when
        commentService.save(dto);

        //then
        Comment comment = commentRepository.findAll().get(0);
        assertThat(comment.getUser().getUserCode()).isEqualTo(dto.getUserCode());
        assertThat(comment.getBoardName()).isEqualTo(dto.getBoardName());
        assertThat(comment.getPostNo()).isEqualTo(dto.getPostNo());
        assertThat(comment.getContent()).isEqualTo(dto.getContent());
    }

    @Test
    public void comment테이블에_댓글_수정하기() {
        //given
        Comment savedComment = this.save_comment_in_repository();

        CommentUpdateRequestDto updateDto = CommentUpdateRequestDto.builder()
                .commentNo(savedComment.getCommentNo())
                .content("댓글 내용 수정")
                .build();

        //when
        commentService.update(savedComment.getCommentNo(), updateDto);

        //then
        Comment comment = commentRepository.findAll().get(0);
        assertThat(comment.getContent()).isEqualTo(updateDto.getContent());
    }

    @Test
    public void comment테이블에_댓글_삭제하기() {
        //given
        Comment savedComment = this.save_comment_in_repository();

        //when
        commentService.delete(savedComment.getCommentNo());

        //then
        assertThat(commentRepository.existsById(savedComment.getPostNo())).isEqualTo(false);
    }

    @Test
    public void comment테이블에서_댓글목록_가져오기() {
        //given
        int givenSize = 5;
        Comment savedComment = this.save_comment_in_repository();
        for(int i=1; i<givenSize; i++) {
            this.save_comment_in_repository();
        }

        //when
        List<CommentListResponseDto> commentList =
                commentService.findAllByPostNoAndBoardName(savedComment.getPostNo(), savedComment.getBoardName());

        //then
        assertThat(commentList.size()).isEqualTo(givenSize);
    }

    @Test
    public void comment테이블에서_댓글갯수_가져오기() {
        //given
        int givenSize = 5;
        Comment savedComment = this.save_comment_in_repository();
        for(int i=1; i<givenSize; i++) {
            this.save_comment_in_repository();
        }

        int actualSize = commentService.countByPostNoAndBoardName(savedComment.getPostNo(), savedComment.getBoardName());

        assertThat(actualSize).isEqualTo(givenSize);
    }

    private Comment save_comment_in_repository() {
        CommentSaveRequestDto dto = CommentSaveRequestDto.builder()
                .userCode(userRepository.findAll().get(0).getUserCode())
                .boardName(MatchNames.Boards.BOARD_QUESTION.getShortName())
                .postNo(questionRepository.findAll().get(0).getPostNo())
                .content("댓글 내용")
                .build();

        return commentRepository.save(dto.toEntity());
    }
}
