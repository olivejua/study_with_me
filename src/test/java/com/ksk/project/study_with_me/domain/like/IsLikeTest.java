package com.ksk.project.study_with_me.domain.like;

import org.junit.After;
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
    IsLikeRepository isLikeRepository;

    @After
    public void cleanup() {
        isLikeRepository.deleteAll();
    }

    @Test
    public void 좋아요저장_불러오기() {
        //given
        Long userCode = 1L;
        Long postNo = 1L;
        boolean isLike = true;

        isLikeRepository.save(IsLike.builder()
                .postNo(postNo)
                .userCode(userCode)
                .isLike(isLike)
                .build());


        //when
        List<IsLike> isLikeList = isLikeRepository.findAll();

        //then
        IsLike like = isLikeList.get(0);
        assertThat(like.getPostNo()).isEqualTo(postNo);
        assertThat(like.isLike()).isEqualTo(isLike);
    }
}
