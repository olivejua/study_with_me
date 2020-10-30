package com.ksk.project.study_with_me.domain;

import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendation;
import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendationRepository;
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
public class BoardPlaceRecommendationTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardPlaceRecommendationRepository placeRepository;

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
        placeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 스터디장소추천게시글저장_불러오기() {
        String title = "게시글 제목 테스트";
        String address = "서울특별시 강남대로";
        String addressDetail = "건물 3층";
        String thumbnailPath = "/resource/photo_upload/5paiel3of_dif7od.jpg";
        String content = "테스트 본문";
        String links = "[www.naver.com, www.google.com, www.daum.net]";
        int likeCount = 3;
        int dislikeCount = 2;
        int viewCount = 10;

        placeRepository.save(BoardPlaceRecommendation.builder()
                .user(userRepository.findAll().get(0))
                .title(title)
                .address(address)
                .addressDetail(addressDetail)
                .thumbnailPath(thumbnailPath)
                .content(content)
                .links(links)
                .likeCount(likeCount)
                .dislikeCount(dislikeCount)
                .viewCount(viewCount)
                .build());

        //when
        List<BoardPlaceRecommendation> placeRecommendationList = placeRepository.findAll();

        //then
        BoardPlaceRecommendation post = placeRecommendationList.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getAddress()).isEqualTo(address);
        assertThat(post.getAddressDetail()).isEqualTo(addressDetail);
        assertThat(post.getThumbnailPath()).isEqualTo(thumbnailPath);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getLinks()).isEqualTo(links);
        assertThat(post.getLikeCount()).isEqualTo(likeCount);
        assertThat(post.getDislikeCount()).isEqualTo(dislikeCount);
        assertThat(post.getViewCount()).isEqualTo(viewCount);
    }
}
