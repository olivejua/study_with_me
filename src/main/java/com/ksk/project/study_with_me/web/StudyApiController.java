package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.config.auth.LoginUser;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.service.ReplyService;
import com.ksk.project.study_with_me.service.RereplyService;
import com.ksk.project.study_with_me.service.StudyService;
import com.ksk.project.study_with_me.web.dto.reply.ReplySaveRequestDto;
import com.ksk.project.study_with_me.web.dto.rereply.RereplySaveRequestDto;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/board/study")
@RestController
public class StudyApiController {

    private final StudyService studyService;
    private final ReplyService replyService;
    private final RereplyService rereplyService;

    @PostMapping("/posts/save")
    public Long save(@RequestBody StudyPostsSaveRequestDto requestDto, @LoginUser SessionUser user) {
        return studyService.save(requestDto.getUser() == null ? requestDto.setUser(user.toEntity()) : requestDto );
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
