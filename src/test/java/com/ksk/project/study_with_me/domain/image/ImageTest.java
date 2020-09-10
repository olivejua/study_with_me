package com.ksk.project.study_with_me.domain.image;

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
public class ImageTest {

    @Autowired
    ImageRepository imageRepository;

    @After
    public void cleanup() {
        imageRepository.deleteAll();
    }

    @Test
    public void 이미지저장_불러오기() {
        //given
        String imageName = "sampleImageName";
        String imagePath = "C:\\Users\\tmfrl\\Desktop\\studywithme_upload";

        imageRepository.save(Image.builder()
                .imageName(imageName)
                .imagePath(imagePath)
                .build());

        //when
        List<Image> imageList = imageRepository.findAll();

        //then
        Image image = imageList.get(0);
        assertThat(image.getImageName()).isEqualTo(imageName);
        assertThat(image.getImagePath()).isEqualTo(imagePath);
    }
}
