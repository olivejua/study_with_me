package com.ksk.project.study_with_me.web;

import com.google.gson.Gson;
import com.ksk.project.study_with_me.config.auth.LoginUser;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.service.ReplyService;
import com.ksk.project.study_with_me.web.dto.reply.ReplyListResponseDto;
import com.ksk.project.study_with_me.web.dto.reply.ReplySaveRequestDto;
import com.ksk.project.study_with_me.web.dto.reply.ReplyUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/board/posts")
@RestController
public class ReplyApiController {
    private final ReplyService replyService;

    @PostMapping("/reply")
    public Long save(@RequestBody ReplySaveRequestDto dto, @LoginUser SessionUser user) {
        return replyService.save(dto.setUser(user.toEntity()));
    }

    @PutMapping("/reply/{replyNo}")
    public Long update(@PathVariable Long replyNo, @RequestBody ReplyUpdateRequestDto dto) {
        return replyService.update(replyNo, dto);
    }

    @DeleteMapping("/reply/{replyNo}")
    public Long delete(@PathVariable Long replyNo) {
        replyService.delete(replyNo);

        return replyNo;
    }

    @GetMapping("/reply/list")
    public String getReplies(String boardName, Long postNo) {
        Gson gson = new Gson();

        List<ReplyListResponseDto> list = replyService.findAllByPostNoAndBoardName(postNo, boardName);

        return  gson.toJson(list);
    }
}
