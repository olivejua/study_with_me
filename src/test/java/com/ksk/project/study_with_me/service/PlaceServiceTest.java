package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendation;
import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendationRepository;
import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
import com.ksk.project.study_with_me.web.dto.place.PostsListResponseDto;
import com.ksk.project.study_with_me.web.dto.place.PostsReadResponseDto;
import com.ksk.project.study_with_me.web.dto.place.PostsSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.place.PostsUpdateRequestDto;
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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaceServiceTest {
    @Autowired
    private BoardPlaceRecommendationRepository placeRepository;

    @Autowired
    private PlaceService placeService;

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
        placeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void place테이블에_post_저장하기() {
        //given
        List<String> links = new ArrayList<>();
        links.add("www.naver.com");
        links.add("www.google.com");
        links.add("www.daum.net");

        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .userCode(userRepository.findAll().get(0).getUserCode())
                .title("게시글 제목 테스트")
                .address("서울특별시 강남대로")
                .addressDetail("건물 3층")
                .thumbnailPath("/resource/photo_upload/5paiel3of_dif7od.jpg")
                .content("테스트 본문")
                .links(links)
                .build();

        //when
        placeService.save(dto);

        //then
        BoardPlaceRecommendation post = placeRepository.findAll().get(0);
        assertThat(post.getUser().getUserCode()).isEqualTo(dto.getUserCode());
        assertThat(post.getTitle()).isEqualTo(dto.getTitle());
        assertThat(post.getAddress()).isEqualTo(dto.getAddress());
        assertThat(post.getAddressDetail()).isEqualTo(dto.getAddressDetail());
        assertThat(post.getThumbnailPath()).isEqualTo(dto.getThumbnailPath());
        assertThat(post.getContent()).isEqualTo(dto.getContent());
        assertThat(post.getLinks()).isEqualTo(dto.getLinks());
    }

    @Test
    public void place테이블에_post_수정하기() {
        //given
        BoardPlaceRecommendation savedPost = this.save_post_in_repository();

        PostsUpdateRequestDto updateDto = PostsUpdateRequestDto.builder()
                .title("테스트 수정")
                .address("경기도 군포시")
                .addressDetail("어딘가에서")
                .oldThumbnailPath("/resource/photo_upload/5paiel3of_dif7od.jpg")
                .thumbnailPath("/resource/photo_upload/2daa257b-21a6-442c-9ef2-83dfb079f0ae.jpg")
                .links(Arrays.asList(savedPost.getLinks().split(",")))
                .content("테스트 본문 수정")
                .build();

        //when
        placeService.update(savedPost.getPostNo(), updateDto);

        BoardPlaceRecommendation post = placeRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo(updateDto.getTitle());
        assertThat(post.getAddress()).isEqualTo(updateDto.getAddress());
        assertThat(post.getAddressDetail()).isEqualTo(updateDto.getAddressDetail());
        assertThat(post.getThumbnailPath()).isEqualTo(updateDto.getThumbnailPath());
        assertThat(post.getLinks()).isEqualTo(updateDto.getLinks());
        assertThat(post.getContent()).isEqualTo(updateDto.getContent());
    }

    @Test
    public void place테이블에_post_삭제하기() {
        BoardPlaceRecommendation savedPost = this.save_post_in_repository();

        placeService.delete(savedPost.getPostNo());

        assertThat(placeRepository.existsById(savedPost.getPostNo())).isEqualTo(false);
    }

    @Test
    public void place테이블에서_postNo로_dto가져오기() {
        BoardPlaceRecommendation savedPost = this.save_post_in_repository();

        PostsReadResponseDto postFound = placeService.findById(savedPost.getPostNo());

        assertThat(postFound.getUser().getUserCode()).isEqualTo(savedPost.getUser().getUserCode());
        assertThat(postFound.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(postFound.getAddress()).isEqualTo(savedPost.getAddress());
        assertThat(postFound.getAddressDetail()).isEqualTo(savedPost.getAddressDetail());
        assertThat(postFound.getThumbnailPath()).isEqualTo(savedPost.getThumbnailPath());
        assertThat(postFound.getLinks().size()).isEqualTo(Arrays.asList(savedPost.getLinks().split(",")).size());
        assertThat(postFound.getContent()).isEqualTo(savedPost.getContent());
    }

    @Test
    public void place테이블에서_dto목록_가져오기() {
        List<String> savedPostTitles = new ArrayList<>();

        for(int i=0; i<5; i++) {
            savedPostTitles.add(this.save_post_in_repository().getTitle());
        }

        Pageable pageable = PageRequest.of(0, 5);

        List<PostsListResponseDto> postList = placeService.findPosts(pageable).getContent();
        for(int i=0; i<postList.size(); i++) {
            assertThat(postList.get(i).getTitle()).isEqualTo(savedPostTitles.get(i));
        }
    }

    private BoardPlaceRecommendation save_post_in_repository() {
        List<String> links = new ArrayList<>();
        links.add("www.naver.com");
        links.add("www.google.com");
        links.add("www.daum.net");

        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .userCode(userRepository.findAll().get(0).getUserCode())
                .title("게시글 제목 테스트")
                .address("서울특별시 강남대로")
                .addressDetail("건물 3층")
                .thumbnailPath("/resource/photo_upload/5paiel3of_dif7od.jpg")
                .content("테스트 본문")
                .links(links)
                .build();

        return placeRepository.save(dto.toEntity());
    }
}
