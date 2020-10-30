package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendation;
import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendationRepository;
import com.ksk.project.study_with_me.domain.like.IsLike;
import com.ksk.project.study_with_me.domain.like.IsLikeRepository;
import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
import com.ksk.project.study_with_me.web.dto.like.LikePlaceSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.place.PostsReadResponseDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LikeServiceTest {

    @Autowired
    LikeService likeService;

    @Autowired
    IsLikeRepository likeRepository;

    @Autowired
    BoardPlaceRecommendationRepository placeRepository;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setup() {
        userRepository.save(User.builder()
                .name("olivejoa")
                .email("olivejoa@github.com")
                .role(Role.USER)
                .nickname("olivejoa")
                .socialCode("google")
                .build());

        placeRepository.save(BoardPlaceRecommendation.builder()
                .user(userRepository.findAll().get(0))
                .title("게시글 제목 테스트")
                .address("서울특별시 강남대로")
                .addressDetail("건물 3층")
                .thumbnailPath("/resource/photo_upload/5paiel3of_dif7od.jpg")
                .content("테스트 본문")
                .links("[www.naver.com, www.google.com, www.daum.net]")
                .likeCount(3)
                .dislikeCount(2)
                .viewCount(10)
                .build());
    }

    @After
    public void cleanup() {
        likeRepository.deleteAll();
        placeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 좋아요_DB에_반영하기() {
        //given
        boolean isLike = true;
        LikePlaceSaveRequestDto dto = LikePlaceSaveRequestDto.builder()
                .userCode(userRepository.findAll().get(0).getUserCode())
                .postNo(placeRepository.findAll().get(0).getPostNo())
                .isLike(isLike)
                .build();

        //when
        likeService.saveOrUpdate(dto);

        //then
        IsLike like = likeRepository.findAll().get(0);
        assertThat(like.isLike()).isEqualTo(isLike);
    }

    @Test
    public void 좋아요_취소_DB에_반영하기() {
        //given
        IsLike like = this.save_like_in_repository();

        //when
        likeService.delete(like.getBoardPlaceRecommendation().getPostNo(), like.getUser().getUserCode());

        //then
        assertThat(likeRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    public void user가_해당post에_좋아요눌렀는지_확인하기() {
        //given
        IsLike like = this.save_like_in_repository();

        //when
        PostsReadResponseDto responseDto =
                likeService.findByUserAndPost(new PostsReadResponseDto(like.getBoardPlaceRecommendation()), like.getUser());

        //then
        assertThat(responseDto.getLike()).isEqualTo(1);
    }

    private IsLike save_like_in_repository() {
        LikePlaceSaveRequestDto dto = LikePlaceSaveRequestDto.builder()
                .userCode(userRepository.findAll().get(0).getUserCode())
                .postNo(placeRepository.findAll().get(0).getPostNo())
                .isLike(true)
                .build();

        return likeRepository.save(dto.toEntity());
    }
}
