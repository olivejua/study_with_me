package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.service.ReplyService;
import com.ksk.project.study_with_me.service.RereplyService;
import com.ksk.project.study_with_me.service.StudyService;
import com.ksk.project.study_with_me.web.dto.reply.ReplyListResponseDto;
import com.ksk.project.study_with_me.web.dto.rereply.RereplyListResponseDto;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsReadResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/board/study")
@Controller
public class StudyController {

    private final StudyService studyService;
    private final ReplyService replyService;
    private final RereplyService rereplyService;

    @GetMapping("/posts/list")
    public String list(Model model) {
        model.addAttribute("posts", studyService.findAllDesc());

        return "/board/study/posts-list";
    }

    @GetMapping("/posts/save")
    public String save() {
        return "/board/study/posts-save";
    }

    @GetMapping("/posts/read")
    public String read(Model model, Long postNo) {
        StudyPostsReadResponseDto responseDto = studyService.findById(postNo);
        List<ReplyListResponseDto> replyResponseDtoList = replyService.findAllByPostNoAndBoardName(postNo, MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getDbName());
        List<RereplyListResponseDto> rereplyResponseDtoList = rereplyService.findAllByPostNoAndBoardName(postNo, MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getDbName());

        model.addAttribute("post", responseDto);
        model.addAttribute("replyList", replyResponseDtoList);

        return "/board/study/posts-read";
    }
}
