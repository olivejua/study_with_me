package com.ksk.project.study_with_me.domain.like;

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
public class IsLikeTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    IsLikeRepository isLikeRepository;

    @After
    public void cleanup() {
        isLikeRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @Test
    public void 좋아요저장_불러오기() {
        //given
        String boardCode = "BPR";
        String boardName = "스터디장소 추천게시판";

        Board board = Board.builder()
                .boardCode(boardCode)
                .boardName(boardName)
                .build();

        boardRepository.save(board);

        Long userCode = 1L;
        boolean isLike = true;

        isLikeRepository.save(IsLike.builder()
                .boardCode(boardCode)
                .userCode(userCode)
                .isLike(isLike)
                .build());


        //when
        List<IsLike> isLikeList = isLikeRepository.findAll();

        //then
        IsLike like = isLikeList.get(0);
        assertThat(like.getBoardCode()).isEqualTo(boardCode);
        assertThat(like.isLike()).isEqualTo(isLike);
    }
}
