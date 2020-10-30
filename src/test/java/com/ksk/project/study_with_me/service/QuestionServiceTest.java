package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestion;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestionRepository;
import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
import com.ksk.project.study_with_me.web.dto.question.PostsListResponseDto;
import com.ksk.project.study_with_me.web.dto.question.PostsReadResponseDto;
import com.ksk.project.study_with_me.web.dto.question.PostsSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.question.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceTest {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private BoardQuestionRepository questionRepository;

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
        questionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void question테이블에_post_저장하기 () {
        //given
        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .userCode(userRepository.findAll().get(0).getUserCode())
                .title("테스트 제목")
                .content("테스트 본문")
                .build();

        questionService.save(dto);

        BoardQuestion post = questionRepository.findAll().get(0);
        assertThat(post.getUser().getUserCode()).isEqualTo(dto.getUserCode());
        assertThat(post.getTitle()).isEqualTo(dto.getTitle());
        assertThat(post.getContent()).isEqualTo(dto.getContent());
    }

    @Test
    public void question테이블에_post_수정하기() {
        //given
        BoardQuestion savedPost = this.save_post_in_repository();

        //when
        PostsUpdateRequestDto updateDto = PostsUpdateRequestDto.builder()
                .title("테스트 제목 수정")
                .content("테스트 본문 수정")
                .build();

        questionService.update(savedPost.getPostNo(), updateDto);

        //then
        BoardQuestion post = questionRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo(updateDto.getTitle());
        assertThat(post.getContent()).isEqualTo(updateDto.getContent());
    }

    @Test
    public void question테이블에_post_삭제하기() {
        //given
        BoardQuestion savedPost = this.save_post_in_repository();

        //when
        questionService.delete(savedPost.getPostNo());

        //then
        assertThat(questionRepository.existsById(savedPost.getPostNo())).isEqualTo(false);
    }

    @Test
    public void question테이블에서_postNo로_dto가져오기() {
        BoardQuestion savedPost = this.save_post_in_repository();

        PostsReadResponseDto postFound = questionService.findById(savedPost.getPostNo());

        assertThat(postFound.getUser().getUserCode()).isEqualTo(savedPost.getUser().getUserCode());
        assertThat(postFound.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(postFound.getContent()).isEqualTo(savedPost.getContent());
    }

    @Test
    public void question테이블에서_dto목록_가져오기() {
        List<String> savedPostTitles = new ArrayList<>();

        for(int i=0; i<5; i++) {
            savedPostTitles.add(this.save_post_in_repository().getTitle());
        }

        Pageable pageable = PageRequest.of(0, 5);

        List<PostsListResponseDto> postList = questionService.findPosts(pageable).getContent();
        for(int i=0; i<postList.size(); i++) {
            assertThat(postList.get(i).getTitle()).isEqualTo(savedPostTitles.get(i));
        }
    }

    private BoardQuestion save_post_in_repository() {
        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .userCode(userRepository.findAll().get(0).getUserCode())
                .title("테스트 제목")
                .content("테스트 본문")
                .build();

        return questionRepository.save(dto.toEntity());
    }
}
