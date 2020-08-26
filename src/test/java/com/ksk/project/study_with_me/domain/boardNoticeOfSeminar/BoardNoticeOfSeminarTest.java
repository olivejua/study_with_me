package com.ksk.project.study_with_me.domain.boardNoticeOfSeminar;

import com.ksk.project.study_with_me.config.MatchNames;
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
    BoardNoticeOfSeminarRepository boardNoticeOfSeminarRepository;

    @After
    public void cleanup() {
        boardNoticeOfSeminarRepository.deleteAll();
    }

    @Test
    public void 세미나알림게시글저장_불러오기() {
        //given
        Long userCode = 1L;
        String title = "세미나 공지 제목";
        String address = "서울특별시 강남구";
        Long imageCodes = 123L;
        int viewCount = 53;

        boardNoticeOfSeminarRepository.save(BoardNoticeOfSeminar.builder()
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
        assertThat(boardNoticeOfSeminar.getBoardName()).isEqualTo(MatchNames.Boards.BOARD_NOTICE_OF_SEMINAR.getName());
        assertThat(boardNoticeOfSeminar.getPostNo()).isEqualTo(1);
        assertThat(boardNoticeOfSeminar.getTitle()).isEqualTo(title);
        assertThat(boardNoticeOfSeminar.getAddress()).isEqualTo(address);
        assertThat(boardNoticeOfSeminar.getViewCount()).isEqualTo(viewCount);
    }
}
