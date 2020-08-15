package com.ksk.project.study_with_me.domain.boardNoticeOfSeminar;

import com.ksk.project.study_with_me.domain.board.Board;
import com.ksk.project.study_with_me.domain.board.BoardRepository;
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
public class BoardNoticeOfSeminarTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardNoticeOfSeminarRepository boardNoticeOfSeminarRepository;

    @After
    public void cleanup() {
        boardNoticeOfSeminarRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @Test
    public void 세미나알림게시글저장_불러오기() {
        //given
        String boardCode = "BNS";
        String boardName = "세미나알림 게시판";

        Board board = Board.builder()
                .boardCode(boardCode)
                .boardName(boardName)
                .build();

        boardRepository.save(board);

        Long userCode = 1L;
        String title = "세미나 공지 제목";
        String address = "서울특별시 강남구";
        Long imageCodes = 123L;
        int viewCount = 53;

        boardNoticeOfSeminarRepository.save(BoardNoticeOfSeminar.builder()
                .board(board)
                .userCode(userCode)
                .title(title)
                .address(address)
                .imageCodes(imageCodes)
                .viewCount(viewCount)
                .build());

        //when
        List<BoardNoticeOfSeminar> boardNoticeOfSeminarList = boardNoticeOfSeminarRepository.findAll();

        //then
        BoardNoticeOfSeminar boardNoticeOfSeminar = boardNoticeOfSeminarList.get(0);
        assertThat(boardNoticeOfSeminar.getBoard().getBoardCode()).isEqualTo(boardCode);
        assertThat(boardNoticeOfSeminar.getPostNo()).isEqualTo(1);
        assertThat(boardNoticeOfSeminar.getTitle()).isEqualTo(title);
        assertThat(boardNoticeOfSeminar.getAddress()).isEqualTo(address);
        assertThat(boardNoticeOfSeminar.getViewCount()).isEqualTo(viewCount);
    }
}
