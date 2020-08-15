package com.ksk.project.study_with_me.domain.reply;

import com.ksk.project.study_with_me.domain.board.Board;
import com.ksk.project.study_with_me.domain.board.BoardRepository;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestion;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestionRepository;
import org.junit.After;
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
    BoardRepository boardRepository;

    @Autowired
    BoardQuestionRepository boardQuestionRepository;

    @Autowired
    ReplyRepository replyRepository;

    @After
    public void cleanup() {
        replyRepository.deleteAll();
        boardQuestionRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @Test
    public void 댓글등록_불러오기() {
        //given
        String boardCode = "BQ";

        Board board = Board.builder()
                .boardCode(boardCode)
                .boardName("질문게시판")
                .build();

        boardRepository.save(board);

        boardQuestionRepository.save(BoardQuestion.builder()
                .board(board)
                .userCode(1L)
                .title("질문 게시글 제목")
                .content("질문 게시글 내용")
                .viewCount(8)
                .replyCount(1)
                .build());

        Long postNo = 1L;
        Long userCode = 2L;
        String content = "질문게시판 댓글";

        replyRepository.save(Reply.builder()
                .boardCode(boardCode)
                .userCode(userCode)
                .postNo(postNo)
                .content(content)
                .build());

        //when
        List<Reply> replyList = replyRepository.findAll();

        //then
        Reply reply = replyList.get(0);
        assertThat(reply.getBoardCode()).isEqualTo(boardCode);
        assertThat(reply.getPostNo()).isEqualTo(postNo);
        assertThat(reply.getUserCode()).isEqualTo(userCode);
        assertThat(reply.getContent()).isEqualTo(content);
    }
}
