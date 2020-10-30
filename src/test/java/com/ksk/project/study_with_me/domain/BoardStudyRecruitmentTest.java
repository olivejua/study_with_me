package com.ksk.project.study_with_me.domain;

import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitment;
import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitmentRepository;
import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardStudyRecruitmentTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardStudyRecruitmentRepository boardStudyRecruitmentRepository;

    @Before
    public void setup() {
        userRepository.save(User.builder()
                .name("olivejoa")
                .email("olivejoa@github.com")
                .role(Role.USER)
                .nickname("olivejoa")
                .socialCode("google")
                .build());
    }

    @After
    public void cleanup() {
        boardStudyRecruitmentRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 스터디모집게시글저장_불러오기() {
        //given
        String title = "스터디 모집 제목";
        String conditionLanguages = "java";
        String conditionPlace = "강남";
        Date conditionStartDate = new Date();
        Date conditionEndDate = new Date();
        int conditionCapacity = 5;
        String conditionExplanation = "스터디 설명";
        int viewCount = 7;
        int commentCount = 2;

        boardStudyRecruitmentRepository.save(BoardStudyRecruitment.builder()
                .user(userRepository.findAll().get(0))
                .title(title)
                .conditionLanguages(conditionLanguages)
                .conditionPlace(conditionPlace)
                .conditionStartDate(conditionStartDate)
                .conditionEndDate(conditionEndDate)
                .conditionCapacity(conditionCapacity)
                .conditionExplanation(conditionExplanation)
                .viewCount(viewCount)
                .commentCount(commentCount)
                .build());

        //when
        List<BoardStudyRecruitment> boardStudyRecruitmentList = boardStudyRecruitmentRepository.findAll();

        //then
        BoardStudyRecruitment boardStudyRecruitment = boardStudyRecruitmentList.get(0);
        assertThat(boardStudyRecruitment.getTitle()).isEqualTo(title);
        assertThat(boardStudyRecruitment.getConditionLanguages()).isEqualTo(conditionLanguages);
        assertThat(boardStudyRecruitment.getConditionPlace()).isEqualTo(conditionPlace);
        assertThat(boardStudyRecruitment.getConditionStartDate()).isEqualTo(new Timestamp(conditionStartDate.getTime()));
        assertThat(boardStudyRecruitment.getConditionEndDate()).isEqualTo(new Timestamp(conditionEndDate.getTime()));
        assertThat(boardStudyRecruitment.getConditionCapacity()).isEqualTo(conditionCapacity);
        assertThat(boardStudyRecruitment.getConditionExplanation()).isEqualTo(conditionExplanation);
        assertThat(boardStudyRecruitment.getViewCount()).isEqualTo(viewCount);
        assertThat(boardStudyRecruitment.getCommentCount()).isEqualTo(commentCount);
    }

    @Test
    public void BaseTimeEntity_등록 () {
        //given
        LocalDateTime now = LocalDateTime.now();
        boardStudyRecruitmentRepository.save(BoardStudyRecruitment.builder()
                .user(userRepository.findAll().get(0))
                .title("테스트 게시글")
                .conditionLanguages("Java, String")
                .conditionPlace("서울특별시 강남대로")
                .conditionStartDate(new Date())
                .conditionEndDate(new Date())
                .conditionCapacity(5)
                .conditionExplanation("테스트 설명")
                .viewCount(0)
                .commentCount(0)
                .build());

        //when
        List<BoardStudyRecruitment> postsList = boardStudyRecruitmentRepository.findAll();

        //then
        BoardStudyRecruitment posts = postsList.get(0);
        assertTrue(posts.getCreatedDate().isAfter(now));
        assertTrue(posts.getModifiedDate().isAfter(now));
    }
}
