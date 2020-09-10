package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.config.auth.LoginUser;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.service.ReplyService;
import com.ksk.project.study_with_me.service.RereplyService;
import com.ksk.project.study_with_me.service.StudyService;
import com.ksk.project.study_with_me.util.FileUtils;
import com.ksk.project.study_with_me.web.dto.reply.ReplySaveRequestDto;
import com.ksk.project.study_with_me.web.dto.rereply.RereplySaveRequestDto;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/board/study")
@RestController
public class StudyApiController {

    private final StudyService studyService;
    private final ReplyService replyService;
    private final RereplyService rereplyService;

    @PostMapping("/posts")
    public Long save(@RequestBody StudyPostsSaveRequestDto requestDto, @LoginUser SessionUser user) {
        //이미지 업로드
        System.out.println("post save() FileUtils.IMAGE_TEMP_PATH : "+FileUtils.IMAGE_TEMP_PATH);

        //img 태그를

        return studyService.save(requestDto.setUser(user.toEntity()));
    }

    @PutMapping("/posts/{postNo}")
    public Long update(@PathVariable Long postNo, @RequestBody StudyPostsUpdateRequestDto requestDto) {
        return studyService.update(postNo, requestDto);
    }

    @DeleteMapping("/posts/{postNo}")
    public Long delete(@PathVariable Long postNo) {
        studyService.delete(postNo);
        return postNo;
    }

    @PostMapping("/posts/reply/save")
    public Long replySave(@RequestBody ReplySaveRequestDto dto, @LoginUser SessionUser user) {
        return replyService.save(dto.setUser(user.toEntity()));
    }

    @PostMapping("/posts/rereply/save")
    public Long rereplySave(@RequestBody RereplySaveRequestDto dto, @LoginUser SessionUser user) {
        return rereplyService.save(dto.setUser(user.toEntity()));
    }
}
