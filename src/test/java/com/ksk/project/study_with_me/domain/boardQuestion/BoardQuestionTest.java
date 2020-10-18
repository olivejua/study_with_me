package com.ksk.project.study_with_me.domain.boardQuestion;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
        String title = "질문 게시글 제목";
        String content = "질문 게시글 내용";
        int viewCount = 8;
        int commentCount = 1;

        boardQuestionRepository.save(BoardQuestion.builder()
                .title(title)
                .content(content)
                .viewCount(viewCount)
                .commentCount(commentCount)
                .build());


        //when
        List<BoardQuestion> boardQuestionList = boardQuestionRepository.findAll();

        //then
//        BoardQuestion boardQuestion = boardQuestionList.get(0);
//        assertThat(boardQuestion.getTitle()).isEqualTo(title);
//        assertThat(boardQuestion.getContent()).isEqualTo(content);
//        assertThat(boardQuestion.getViewCount()).isEqualTo(viewCount);
//        assertThat(boardQuestion.getCommentCount()).isEqualTo(commentCount);
    }
}
