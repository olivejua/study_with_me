package com.ksk.project.study_with_me.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendation;
import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendationRepository;
import com.ksk.project.study_with_me.domain.like.IsLike;
import com.ksk.project.study_with_me.domain.like.IsLikeRepository;
import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
import com.ksk.project.study_with_me.web.dto.like.LikePlaceSaveRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LikeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private IsLikeRepository likeRepository;

    @Autowired
    private BoardPlaceRecommendationRepository placeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private MockHttpSession httpSession;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

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
                .likeCount(0)
                .dislikeCount(0)
                .viewCount(10)
                .build());

        User sampleUser = userRepository.findAll().get(0);
        httpSession = new MockHttpSession();
        httpSession.setAttribute("user", new SessionUser(sampleUser));
    }

    @After
    public void tearDown() throws Exception {
        likeRepository.deleteAll();
        placeRepository.deleteAll();
        httpSession.clearAttributes();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 좋아요_클릭() throws Exception {
        //given
        User user = userRepository.findAll().get(0);
        BoardPlaceRecommendation post = placeRepository.findAll().get(0);
        boolean isLike = true;

        LikePlaceSaveRequestDto requestDto = LikePlaceSaveRequestDto.builder()
                .userCode(user.getUserCode())
                .postNo(post.getPostNo())
                .isLike(isLike)
                .build();

        String url = "http://localhost:" + port + "/board/place/posts/like";

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 좋아요_취소() throws Exception {
        //given
        IsLike savedLike = likeRepository.save(IsLike.builder()
                .user(userRepository.findAll().get(0))
                .boardPlaceRecommendation(placeRepository.findAll().get(0))
                .isLike(true)
                .build());

        String url = "http://localhost:" + port + "/board/place/posts/like/"
                + savedLike.getBoardPlaceRecommendation().getPostNo() + "/" + savedLike.getUser().getUserCode();

        //when
        mvc.perform(delete(url))
                .andExpect(status().isOk());
    }
}
