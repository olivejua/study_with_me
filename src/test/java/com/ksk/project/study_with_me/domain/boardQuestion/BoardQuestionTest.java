package com.ksk.project.study_with_me.domain.boardQuestion;

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
public class BoardQuestionTest {

    @Autowired
    BoardQuestionRepository boardQuestionRepository;

    @After
    public void cleanup() {
        boardQuestionRepository.deleteAll();
    }

    @Test
    public void 질문게시글저장_불러오기() {
        //given
        Long userCode = 1L;
        String title = "질문 게시글 제목";
        String content = "질문 게시글 내용";
        Long imageCodes = 123L;
        int viewCount = 8;
        int replyCount = 1;

        boardQuestionRepository.save(BoardQuestion.builder()
                .userCode(userCode)
                .title(title)
                .content(content)
                .viewCount(viewCount)
                .replyCount(replyCount)
                .build());


        //when
        List<BoardQuestion> boardQuestionList = boardQuestionRepository.findAll();

        //then
        BoardQuestion boardQuestion = boardQuestionList.get(0);
        assertThat(boardQuestion.getBoardName()).isEqualTo(MatchNames.Boards.BOARD_QUESTION.getName());
        assertThat(boardQuestion.getUserCode()).isEqualTo(userCode);
        assertThat(boardQuestion.getTitle()).isEqualTo(title);
        assertThat(boardQuestion.getContent()).isEqualTo(content);
        assertThat(boardQuestion.getImageCodes()).isEqualTo(null);
        assertThat(boardQuestion.getViewCount()).isEqualTo(viewCount);
        assertThat(boardQuestion.getReplyCount()).isEqualTo(replyCount);
    }
}
