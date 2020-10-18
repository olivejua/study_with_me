package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.config.auth.LoginUser;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.service.QuestionService;
import com.ksk.project.study_with_me.service.CommentService;
import com.ksk.project.study_with_me.service.ReplyService;
import com.ksk.project.study_with_me.web.dto.question.PostsReadResponseDto;
import com.ksk.project.study_with_me.web.dto.question.PostsSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.question.PostsUpdateRequestDto;
import com.ksk.project.study_with_me.web.file.TransferFiles;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/board/question")
@RestController
public class QuestionApiController {

    private final QuestionService questionService;
    private final CommentService commentService;
    private final ReplyService replyService;

    @PostMapping("/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        Long savedPostNo = questionService.save(requestDto);

        TransferFiles.saveImagesByHtmlCode(requestDto.getContent()
                , MatchNames.Boards.BOARD_QUESTION.getShortName(), savedPostNo);

        return savedPostNo;
    }

    @GetMapping("/posts")
    public ModelAndView read(Long postNo, @LoginUser SessionUser user, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/question/posts-read");

        PostsReadResponseDto responseDto = questionService.findById(postNo);
        String boardName = MatchNames.Boards.BOARD_QUESTION.getShortName();

        mav.addObject("user", user);
        mav.addObject("post", responseDto);
//        mav.addObject("commentList", commentService.findAllByPostNoAndBoardName(postNo, boardName));
//        mav.addObject("replyList", replyService.findAllByPostNoAndBoardName(postNo, boardName));

        TransferFiles.readImagesByHtmlCode(responseDto.getContent()
                , request.getSession().getServletContext().getRealPath("/"), boardName, postNo);

        return mav;
    }

    @PutMapping("/posts/{postNo}")
    public Long update(@PathVariable Long postNo, @RequestBody PostsUpdateRequestDto requestDto) {
        TransferFiles.updateImagesByHtmlCode(requestDto.getContent(), MatchNames.Boards.BOARD_QUESTION.getShortName(), postNo);

        return questionService.update(postNo, requestDto);
    }

    @DeleteMapping("/posts/{postNo}")
    public Long delete(@PathVariable Long postNo) {
        TransferFiles.deleteAllImagesInDirectory(MatchNames.Boards.BOARD_QUESTION.getShortName(), postNo);

        return questionService.delete(postNo);
    }

    @GetMapping("/posts/list")
    public ModelAndView findPosts(@PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/question/posts-list");

        mav.addObject("list", questionService.findPosts(pageable));

        return mav;
    }

    @GetMapping("/posts/save")
    public ModelAndView save(@LoginUser SessionUser user) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/question/posts-save");
        mav.addObject("user", user);

        return mav;
    }

    @GetMapping("/posts/update")
    public ModelAndView update(Long postNo) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/question/posts-update");

        mav.addObject("post", questionService.findById(postNo));

        return mav;
    }
}
