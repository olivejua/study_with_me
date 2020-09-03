package com.ksk.project.study_with_me.domain.boardStudyRecruitment;

import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
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
    UserRepository userRepository;

    @Autowired
    BoardStudyRecruitmentRepository boardStudyRecruitmentRepository;

    @After
    public void cleanup() {
        boardStudyRecruitmentRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 스터디모집게시글저장_불러오기() {
        //given

        User user = User.builder()
                .name("olivejoa")
                .nickname("olivejoa")
                .email("tmfrl4710@gmail.com")
                .role(Role.USER)
                .socialCode("google")
                .build();

        String title = "스터디 모집 제목";
        String conditionLanguages = "java";
        String conditionPlace = "강남";
        Date conditionStartDate = new Date();
        Date conditionEndDate = new Date();
        int conditionCapacity = 5;
        String conditionExplanation = "스터디 설명";
        int viewCount = 7;
        int replyCount = 2;

        userRepository.save(user);

        boardStudyRecruitmentRepository.save(BoardStudyRecruitment.builder()
                .user(user)
                .title(title)
                .conditionLanguages(conditionLanguages)
                .conditionPlace(conditionPlace)
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
        assertThat(boardStudyRecruitment.getConditionLanguages()).isEqualTo(conditionLanguages);
        assertThat(boardStudyRecruitment.getConditionCapacity()).isEqualTo(conditionCapacity);
    }
}
