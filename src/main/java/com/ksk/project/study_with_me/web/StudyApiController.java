package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.config.auth.LoginUser;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.service.CommentService;
import com.ksk.project.study_with_me.service.ReplyService;
import com.ksk.project.study_with_me.service.StudyService;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsReadResponseDto;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsUpdateRequestDto;
import com.ksk.project.study_with_me.web.dto.study.StudySearchResponseDto;
import com.ksk.project.study_with_me.web.file.TransferFiles;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/board/study")
@RestController
public class StudyApiController {

    private final StudyService studyService;
    private final CommentService commentService;
    private final ReplyService replyService;

    @PostMapping("/posts")
    public Long save(@RequestBody StudyPostsSaveRequestDto requestDto, @LoginUser SessionUser user) {
        Long savedPostNo = studyService.save(requestDto.setUser(user.toEntity()));

        TransferFiles.saveImagesByHtmlCode(requestDto.getConditionExplanation()
                , MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getShortName(), savedPostNo);

        return savedPostNo;
    }

    @PutMapping("/posts/{postNo}")
    public Long update(@PathVariable Long postNo, @RequestBody StudyPostsUpdateRequestDto requestDto) {

        TransferFiles.updateImagesByHtmlCode(requestDto.getConditionExplanation()
                , MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getShortName(), postNo);

        return studyService.update(postNo, requestDto);
    }

    @DeleteMapping("/posts/{postNo}")
    public Long delete(@PathVariable Long postNo) {
        studyService.delete(postNo);

        TransferFiles.deleteAllImagesInDirectory(MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getShortName(), postNo);

        return postNo;
    }

    @GetMapping("/posts/list")
    public ModelAndView findPosts(@PageableDefault(size = 10, sort="createdDate", direction = Sort.Direction.DESC) Pageable pageRequest) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/study/posts-list");

        mav.addObject("list", studyService.findPosts(pageRequest));

        return mav;
    }

    @GetMapping("/search/{searchType}/{keyword}/list")
    public ModelAndView findPosts( @PathVariable String searchType, @PathVariable String keyword,
                             @PageableDefault(size = 10, sort="createdDate", direction = Sort.Direction.DESC) Pageable pageRequest) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/study/posts-list");

        mav.addObject("list", studyService.searchPosts(pageRequest, searchType, keyword));
        mav.addObject("search", new StudySearchResponseDto(searchType, keyword));

        return mav;
    }

    @GetMapping("/posts/save")
    public ModelAndView save() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/study/posts-save");

        return mav;
    }

    @GetMapping("/posts")
    public ModelAndView read(Long postNo, @LoginUser SessionUser user, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/study/posts-read");

        StudyPostsReadResponseDto responseDto = studyService.findById(postNo);
        String boardName =  MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getShortName();

        mav.addObject("user", user);
        mav.addObject("post", responseDto);
        mav.addObject("commentList", commentService.findAllByPostNoAndBoardName(postNo, boardName));
        mav.addObject("replyList", replyService.findAllByPostNoAndBoardName(postNo, boardName));

        TransferFiles.readImagesByHtmlCode(responseDto.getConditionExplanation()
                , request.getSession().getServletContext().getRealPath("/"), boardName, postNo);

        return mav;
    }

    @GetMapping("/posts/update")
    public ModelAndView update(Long postNo) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/study/posts-update");

        mav.addObject("post", studyService.findById(postNo));

        return mav;
    }
}
