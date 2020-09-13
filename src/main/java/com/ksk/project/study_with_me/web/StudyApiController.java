package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.config.auth.LoginUser;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.service.StudyService;
import com.ksk.project.study_with_me.util.ImageUtils;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/board/study")
@RestController
public class StudyApiController {

    private final StudyService studyService;

    @PostMapping("/posts")
    public Long save(@RequestBody StudyPostsSaveRequestDto requestDto, @LoginUser SessionUser user) {
        Long savedPostNo = studyService.save(requestDto.setUser(user.toEntity()));

        //이미지 업로드
        if(ImageUtils.existImages(requestDto.getConditionExplanation())) {
            System.out.println("post save() FileUtils.IMAGE_TEMP_PATH : "+ ImageUtils.TEMP_PATH);

            List<String> imageList = ImageUtils.findImageInHTMLCode(requestDto.getConditionExplanation());
            if (imageList.size() > 0) {
                ImageUtils.saveImages(imageList, MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getShortName(), savedPostNo);
            }
        }

        return savedPostNo;
    }

    @PutMapping("/posts/{postNo}")
    public Long update(@PathVariable Long postNo, @RequestBody StudyPostsUpdateRequestDto requestDto) {

        ImageUtils.deleteDirectory(
                new File(ImageUtils.DEFAULT_PATH + MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getShortName() + File.separator + postNo));

        if(ImageUtils.existImages(requestDto.getConditionExplanation())) {
            System.out.println("post save() FileUtils.IMAGE_TEMP_PATH : "+ ImageUtils.TEMP_PATH);

            List<String> imageList = ImageUtils.findImageInHTMLCode(requestDto.getConditionExplanation());
            if (imageList.size() > 0) {
                ImageUtils.saveImages(imageList, MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getShortName(), postNo);
            }
        }

        return studyService.update(postNo, requestDto);
    }

    @DeleteMapping("/posts/{postNo}")
    public Long delete(@PathVariable Long postNo) {
        studyService.delete(postNo);

        ImageUtils.deleteDirectory(
                new File(ImageUtils.DEFAULT_PATH + MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getShortName() + File.separator + postNo));

        return postNo;
    }
}
