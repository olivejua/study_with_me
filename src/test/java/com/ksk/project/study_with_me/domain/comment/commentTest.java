package com.ksk.project.study_with_me.domain.comment;

import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestion;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestionRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class commentTest {

    @Autowired
    BoardQuestionRepository boardQuestionRepository;

    @Autowired
    CommentRepository commentRepository;

    @After
    public void cleanup() {
        commentRepository.deleteAll();
        boardQuestionRepository.deleteAll();
    }

    @Test
    public void 댓글등록_불러오기() {
        //given
        boardQuestionRepository.save(BoardQuestion.builder()
                .title("질문 게시글 제목")
                .content("질문 게시글 내용")
                .viewCount(8)
                .commentCount(1)
                .build());

        Long postNo = 1L;
        String content = "질문게시판 댓글";

//        commentRepository.save(Comment.builder()
//                .boardName(MatchNames.Boards.BOARD_QUESTION.getCalledName())
//                .user(userCode)
//                .postNo(postNo)
//                .content(content)
//                .build());
//
//        //when
//        List<Comment> commentList = commentRepository.findAll();
//
//        //then
//        Comment comment = commentList.get(0);
//        assertThat(comment.getPostNo()).isEqualTo(postNo);
//        assertThat(comment.getUserCode()).isEqualTo(userCode);
//        assertThat(comment.getContent()).isEqualTo(content);
    }
}
