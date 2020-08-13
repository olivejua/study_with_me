package com.ksk.project.study_with_me.domain.boardStudyRecruitment;

import com.ksk.project.study_with_me.domain.board.Board;
import com.ksk.project.study_with_me.domain.board.BoardRepository;
import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitment;
import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitmentRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardStudyRecruitmentTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardStudyRecruitmentRepository boardStudyRecruitmentRepository;

    @After
    public void cleanup() {
        boardStudyRecruitmentRepository.deleteAll();
        boardRepository.deleteAll();
    }

    @Test
    public void 스터디모집게시글저장_불러오기() {
        //given
        String boardCode = "BSR";
        String boardName = "스터디모집게시판";

        boardRepository.save(Board.builder()
                .boardCode(boardCode)
                .boardName(boardName)
                .build());


        Long userCode = 1L;
        String title = "스터디 모집 제목";
        String conditionLanguages = "java";
        String conditionRegion = "강남";
        Date conditionStartDate = new Date();
        Date conditionEndDate = new Date();
        int conditionCapacity = 5;
        String conditionExplanation = "스터디 설명";
        int viewCount = 7;
        int replyCount = 2;

        boardStudyRecruitmentRepository.save(BoardStudyRecruitment.builder()
                .boardCode(boardCode)
                .userCode(userCode)
                .title(title)
                .conditionLanguages(conditionLanguages)
                .conditionRegion(conditionRegion)
                .conditionStartDate(conditionStartDate)
                .conditionEndDate(conditionEndDate)
                .conditionCapacity(conditionCapacity)
                .conditionExplanation(conditionExplanation)
                .viewCount(viewCount)
                .replyCount(replyCount)
                .build());

        //when
        List<BoardStudyRecruitment> boardStudyRecruitmentList = boardStudyRecruitmentRepository.findAll();

        //then
        BoardStudyRecruitment boardStudyRecruitment = boardStudyRecruitmentList.get(0);
        assertThat(boardStudyRecruitment.getBoardCode()).isEqualTo(boardCode);
        assertThat(boardStudyRecruitment.getConditionLanguages()).isEqualTo(conditionLanguages);
        assertThat(boardStudyRecruitment.getConditionCapacity()).isEqualTo(conditionCapacity);
    }
}
