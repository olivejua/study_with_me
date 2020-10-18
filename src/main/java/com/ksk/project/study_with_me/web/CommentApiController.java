package com.ksk.project.study_with_me.web;

import com.google.gson.Gson;
import com.ksk.project.study_with_me.service.CommentService;
import com.ksk.project.study_with_me.web.dto.comment.CommentSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.comment.CommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/board/posts")
@RestController
public class CommentApiController {
    private final CommentService commentService;

    @PostMapping("/comment")
    public Long save(@RequestBody CommentSaveRequestDto requestDto) {
        return commentService.save(requestDto);
    }

    @PutMapping("/comment/{commentNo}")
    public Long update(@PathVariable Long commentNo, @RequestBody CommentUpdateRequestDto dto) {
        return commentService.update(commentNo, dto);
    }

    @DeleteMapping("/comment/{commentNo}")
    public Long delete(@PathVariable Long commentNo) {
        commentService.delete(commentNo);

        return commentNo;
    }

    @GetMapping("/comment/list")
    public String getComments(String boardName, Long postNo) {
        Gson gson = new Gson();
        return  gson.toJson(commentService.findAllByPostNoAndBoardName(postNo, boardName));
    }
}
