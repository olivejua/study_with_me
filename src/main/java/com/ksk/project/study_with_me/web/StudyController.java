package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.config.auth.LoginUser;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.service.ReplyService;
import com.ksk.project.study_with_me.service.RereplyService;
import com.ksk.project.study_with_me.service.StudyService;
import com.ksk.project.study_with_me.util.ImageUtils;
import com.ksk.project.study_with_me.web.dto.reply.ReplyListResponseDto;
import com.ksk.project.study_with_me.web.dto.rereply.RereplyListResponseDto;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsReadResponseDto;
import com.ksk.project.study_with_me.web.dto.study.StudySearchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/board/study")
@Controller
public class StudyController {

    private final StudyService studyService;
    private final ReplyService replyService;
    private final RereplyService rereplyService;

    @GetMapping("/posts/list")
    public String  findPosts(@PageableDefault(size = 10, sort="createdDate", direction = Sort.Direction.DESC) Pageable pageRequest, Model model) {
        model.addAttribute("list", studyService.findPosts(pageRequest));

        return "/board/study/posts-list";
    }

    @GetMapping("/search/{searchType}/{keyword}/list")
    public String findPosts( @PathVariable String searchType, @PathVariable String keyword,
                             @PageableDefault(size = 10, sort="createdDate", direction = Sort.Direction.DESC) Pageable pageRequest,
                                                 Model model) {
        model.addAttribute("list", studyService.searchPosts(pageRequest, searchType, keyword));
        model.addAttribute("search", new StudySearchResponseDto(searchType, keyword));

        return "board/study/posts-list";
    }

    @GetMapping("/posts/save")
    public String save() {
        return "board/study/posts-save";
    }

    @GetMapping("/posts/read")
    public String read(Model model, Long postNo, @LoginUser SessionUser user, HttpServletRequest request) {
        StudyPostsReadResponseDto responseDto = studyService.findById(postNo);
        List<ReplyListResponseDto> replyResponseDtoList = replyService.findAllByPostNoAndBoardName(postNo, MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getShortName());
        List<RereplyListResponseDto> rereplyResponseDtoList = rereplyService.findAllByPostNoAndBoardName(postNo, MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getShortName());

        model.addAttribute("user", user);
        model.addAttribute("post", responseDto);
        model.addAttribute("replyList", replyResponseDtoList);
        model.addAttribute("rereplyList", rereplyResponseDtoList);


        if(ImageUtils.existImages(responseDto.getConditionExplanation())) {
            String defaultPath = request.getSession().getServletContext().getRealPath("/");
            String copy_path = defaultPath + "resource" + File.separator + "photo_upload" + File.separator;

            String sourcePath = ImageUtils.DEFAULT_PATH + MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getShortName() + File.separator + postNo;

            ImageUtils.copyAllImagesInDir(
                     sourcePath, copy_path);
        }

        return "board/study/posts-read";
    }

    @GetMapping("/posts/update")
    public String update(Model model, Long postNo) {
        StudyPostsReadResponseDto responseDto = studyService.findById(postNo);
        model.addAttribute("post", responseDto);

        return "board/study/posts-update";
    }
}