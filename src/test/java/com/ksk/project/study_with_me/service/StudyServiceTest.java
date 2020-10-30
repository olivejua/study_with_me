package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitment;
import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitmentRepository;
import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
import com.ksk.project.study_with_me.web.dto.study.PostsListResponseDto;
import com.ksk.project.study_with_me.web.dto.study.PostsReadResponseDto;
import com.ksk.project.study_with_me.web.dto.study.PostsSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.study.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudyServiceTest {

    @Autowired
    private StudyService studyService;

    @Autowired
    private BoardStudyRecruitmentRepository studyRepository;

    @Autowired
    private UserRepository userRepository;

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
        studyRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void study테이블에_post_저장하기 () {
        //given
        List<String> languages = new ArrayList<>();
        languages.add("spring boot");
        languages.add("jpa");
        languages.add("java");

        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .user(userRepository.findAll().get(0))
                .title("스터디 모집 제목")
                .conditionLanguages(languages)
                .conditionPlace("서울특별시 강남대로")
                .conditionStartDate(new Date())
                .conditionEndDate(new Date())
                .conditionCapacity(5)
                .conditionExplanation("스터디 설명")
                .build();

        //when
        studyService.save(dto);

        //then
        BoardStudyRecruitment post = studyRepository.findAll().get(0);
        assertThat(post.getUser().getUserCode()).isEqualTo(dto.getUser().getUserCode());
        assertThat(post.getTitle()).isEqualTo(dto.getTitle());
        assertThat(post.getConditionLanguages()).isEqualTo(dto.getConditionLanguages());
        assertThat(post.getConditionPlace()).isEqualTo(dto.getConditionPlace());
        assertThat(post.getConditionStartDate()).isEqualTo(new Timestamp(dto.getConditionStartDate().getTime()));
        assertThat(post.getConditionEndDate()).isEqualTo(new Timestamp(dto.getConditionEndDate().getTime()));
        assertThat(post.getConditionCapacity()).isEqualTo(dto.getConditionCapacity());
        assertThat(post.getConditionExplanation()).isEqualTo(dto.getConditionExplanation());
    }

    @Test
    public void study테이블에_post_수정하기() {
        //given
        BoardStudyRecruitment savedPost = this.save_post_in_repository();

        PostsUpdateRequestDto updateDto = PostsUpdateRequestDto.builder()
                .userCode(savedPost.getUser().getUserCode())
                .title("제목 수정")
                .conditionLanguages(Arrays.asList(savedPost.getConditionLanguages().split(",")))
                .conditionPlace("경기도 군포시")
                .conditionStartDate(savedPost.getConditionStartDate())
                .conditionEndDate(savedPost.getConditionEndDate())
                .conditionCapacity(8)
                .conditionExplanation("본문 수정")
                .build();

        //when
        studyService.update(savedPost.getPostNo(), updateDto);

        //then
        BoardStudyRecruitment post = studyRepository.findAll().get(0);
        assertThat(post.getUser().getUserCode()).isEqualTo(updateDto.getUserCode());
        assertThat(post.getTitle()).isEqualTo(updateDto.getTitle());
        assertThat(post.getConditionLanguages()).isEqualTo(updateDto.getConditionLanguages());
        assertThat(post.getConditionPlace()).isEqualTo(updateDto.getConditionPlace());
        assertThat(post.getConditionStartDate()).isEqualTo(new Timestamp(updateDto.getConditionStartDate().getTime()));
        assertThat(post.getConditionEndDate()).isEqualTo(new Timestamp(updateDto.getConditionEndDate().getTime()));
        assertThat(post.getConditionCapacity()).isEqualTo(updateDto.getConditionCapacity());
        assertThat(post.getConditionExplanation()).isEqualTo(updateDto.getConditionExplanation());
    }

    @Test
    public void study테이블에_post_삭제하기() {
        //given
        BoardStudyRecruitment savedPost = this.save_post_in_repository();

        //when
        studyService.delete(savedPost.getPostNo());

        //then
        assertThat(studyRepository.existsById(savedPost.getPostNo())).isEqualTo(false);
    }

    @Test
    public void study테이블에서_postNo로_dto가져오기() {
        BoardStudyRecruitment savedPost = this.save_post_in_repository();

        PostsReadResponseDto postFound = studyService.findById(savedPost.getPostNo());

        assertThat(postFound.getUser().getUserCode()).isEqualTo(savedPost.getUser().getUserCode());
        assertThat(postFound.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(postFound.getConditionLanguages().size()).isEqualTo(Arrays.asList(savedPost.getConditionLanguages().split(",")).size());
        assertThat(postFound.getConditionPlace()).isEqualTo(savedPost.getConditionPlace());
        assertThat(postFound.getConditionStartDate()).isEqualTo(new Timestamp(savedPost.getConditionStartDate().getTime()));
        assertThat(postFound.getConditionEndDate()).isEqualTo(new Timestamp(savedPost.getConditionEndDate().getTime()));
        assertThat(postFound.getConditionCapacity()).isEqualTo(savedPost.getConditionCapacity());
        assertThat(postFound.getConditionExplanation()).isEqualTo(savedPost.getConditionExplanation());
    }

    @Test
    public void study테이블에서_dto목록_가져오기() {
        List<String> savedPostTitles = new ArrayList<>();

        for(int i=0; i<5; i++) {
            savedPostTitles.add(this.save_post_in_repository().getTitle());
        }

        Pageable pageable = PageRequest.of(0, 5);

        List<PostsListResponseDto> postList = studyService.findPosts(pageable).getContent();
        for(int i=0; i<postList.size(); i++) {
            assertThat(postList.get(i).getTitle()).isEqualTo(savedPostTitles.get(i));
        }
    }

    private BoardStudyRecruitment save_post_in_repository() {
        List<String> languages = new ArrayList<>();
        languages.add("spring boot");
        languages.add("jpa");
        languages.add("java");

        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .user(userRepository.findAll().get(0))
                .title("스터디 모집 제목")
                .conditionLanguages(languages)
                .conditionPlace("서울특별시 강남대로")
                .conditionStartDate(new Date())
                .conditionEndDate(new Date())
                .conditionCapacity(5)
                .conditionExplanation("스터디 설명")
                .build();

        return studyRepository.save(dto.toEntity());
    }
}
