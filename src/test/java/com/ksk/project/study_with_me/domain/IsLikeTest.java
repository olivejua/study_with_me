package com.ksk.project.study_with_me.domain;

import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendation;
import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendationRepository;
import com.ksk.project.study_with_me.domain.like.IsLike;
import com.ksk.project.study_with_me.domain.like.IsLikeRepository;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IsLikeTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardPlaceRecommendationRepository placeRepository;

    @Autowired
    IsLikeRepository isLikeRepository;

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
        isLikeRepository.deleteAll();
        placeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 좋아요_불러오기() {
        //given
        User user = userRepository.findAll().get(0);
        BoardPlaceRecommendation post = placeRepository.findAll().get(0);
        boolean isLike = true;

        isLikeRepository.save(IsLike.builder()
                .user(user)
                .boardPlaceRecommendation(post)
                .isLike(isLike)
                .build());


        //when
        List<IsLike> isLikeList = isLikeRepository.findAll();

        //then
        IsLike like = isLikeList.get(0);
        assertThat(like.getUser().getUserCode()).isEqualTo(user.getUserCode());
        assertThat(like.getBoardPlaceRecommendation().getPostNo()).isEqualTo(post.getPostNo());
        assertThat(like.isLike()).isEqualTo(isLike);
    }
}
