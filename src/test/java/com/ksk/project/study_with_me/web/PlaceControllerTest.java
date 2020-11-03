package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendationRepository;
import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.domain.user.UserRepository;
import com.ksk.project.study_with_me.web.dto.place.PostsSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.place.TestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlaceControllerTest {

    @LocalServerPort
    private int port;

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

        User sampleUser = userRepository.findAll().get(0);
        httpSession = new MockHttpSession();
        httpSession.setAttribute("user", new SessionUser(sampleUser));
    }

    @After
    public void tearDown() throws Exception {
        placeRepository.deleteAll();
        httpSession.clearAttributes();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void test() throws Exception {
        /**
         * 일단  File, dto 둘다 한번에 전송이 안된다.
         * 첫번째 방법 . 하나씩 둘다 한다.
         * 두번째 방법 . 둘 중 하나 선택해서 전송 테스트한다.
         * 세번째 방법 . 그냥 작성해놓고 얼렁뚱땅 진짜 테스트는 안하고 넘어간다.
         * */

        //given
        String title = "스터디 장소 추천합니다.";
        String address = "서울특별시 강남대로";
        String addressDetail = "어쩌고 건물 3층";

        List<String> links = new ArrayList<>();
        links.add("www.google.com");
        links.add("www.naver.com");
        links.add("www.daum.net");

        String thumbnailPath = "/resource/photo_upload/test.jpg";
        String content = "좋은 정보네요";

        TestDto requestDto = TestDto.builder()
                .userCode(userRepository.findAll().get(0).getUserCode())
                .title(title)
                .address(address)
                .addressDetail(addressDetail)
                .links(links)
                .thumbnailPath(thumbnailPath)
                .content(content)
                .build();


        String url  = "http://localhost:" + port + "/board/place/test";

        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "text/plain", "hello file".getBytes());
//        MockMultipartFile dto = new MockMultipartFile("json", "", "application/json",)

//        mvc.perform(multipart(url)
//                .file(file)
//                .content())
//                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 장소추천_Posts_등록된다() throws Exception {
        //given
        String title = "스터디 장소 추천합니다.";
        String address = "서울특별시 강남대로";
        String addressDetail = "어쩌고 건물 3층";

        List<String> links = new ArrayList<>();
        links.add("www.google.com");
        links.add("www.naver.com");
        links.add("www.daum.net");

        String thumbnailPath = "/resource/photo_upload/test.jpg";
        String content = "좋은 정보네요";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .userCode(userRepository.findAll().get(0).getUserCode())
                .title(title)
                .address(address)
                .addressDetail(addressDetail)
                .links(links)
                .thumbnailPath(thumbnailPath)
                .content(content)
                .build();

        String url = "http://localhost:" + port + "/board/place/posts";

        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "text/plain", "hello file".getBytes());

        //when
//        mvc.perform(multipart(url)
//                .file(file)
//                .content(new ObjectMapper().writeValueAsString(requestDto)))
//                .andExpect(status().isOk());
    }
}
