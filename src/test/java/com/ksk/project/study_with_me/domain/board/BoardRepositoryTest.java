package com.ksk.project.study_with_me.domain.board;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest //H2 데이터베이스를 자동으로 실행시켜준다.
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @After
    public void cleanup() {
        boardRepository.deleteAll();
    }

    @Test
    public void 게시판저장_불러오기() {
        //given
        String boardCode = "BQ";
        String boardName = "질문게시판";

        boardRepository.save(Board.builder()
                .boardCode(boardCode)
                .boardName(boardName)
                .build());

        //when
        List<Board> boardList = boardRepository.findAll();

        //then
        Board board = boardList.get(0);
        assertThat(board.getBoardCode()).isEqualTo(boardCode);
        assertThat(board.getBoardName()).isEqualTo(boardName);
    }
}
