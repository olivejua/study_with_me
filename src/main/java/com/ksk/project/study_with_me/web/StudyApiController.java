package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.config.auth.LoginUser;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.service.CommentService;
import com.ksk.project.study_with_me.service.ReplyService;
import com.ksk.project.study_with_me.service.StudyService;
import com.ksk.project.study_with_me.web.dto.PageDto;
import com.ksk.project.study_with_me.web.dto.study.*;
import com.ksk.project.study_with_me.web.file.TransferFiles;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public Long save(@RequestBody PostsSaveRequestDto requestDto, @LoginUser SessionUser user) {
        Long savedPostNo = studyService.save(requestDto.setUser(user.toEntity()));

        TransferFiles.saveImagesByHtmlCode(requestDto.getConditionExplanation()
                , MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getShortName(), savedPostNo);

        return savedPostNo;
    }

    @PutMapping("/posts/{postNo}")
    public Long update(@PathVariable Long postNo, @RequestBody PostsUpdateRequestDto requestDto) {

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


    @GetMapping("/list")
    public ModelAndView findPosts(@PageableDefault(size = 10, sort="createdDate", direction = Sort.Direction.DESC) Pageable pageRequest
                                  , SearchDto searchDto) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/study/posts-list");

        Page<PostsListResponseDto> postList = searchDto.existSearch() ?
                studyService.searchPosts(pageRequest, searchDto) : studyService.findPosts(pageRequest);

        mav.addObject("list", postList);
        mav.addObject("search", searchDto.existSearch() ? searchDto : null);

        return mav;
    }

    @GetMapping("/posts/save")
    public ModelAndView save() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/study/posts-save");

        return mav;
    }

    @GetMapping("/posts")
    public ModelAndView read(Long postNo, @LoginUser SessionUser user, HttpServletRequest request, PageDto pageDto) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/study/posts-read");

        PostsReadResponseDto responseDto = studyService.findById(postNo).savePageInfo(pageDto);
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
