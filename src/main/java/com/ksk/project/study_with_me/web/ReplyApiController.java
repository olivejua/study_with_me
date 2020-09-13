package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.config.auth.LoginUser;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.service.ReplyService;
import com.ksk.project.study_with_me.web.dto.reply.ReplySaveRequestDto;
import com.ksk.project.study_with_me.web.dto.reply.ReplyUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/board")
@RestController
public class ReplyApiController {
    private final ReplyService replyService;

    @PostMapping("/posts/reply")
    public Long save(@RequestBody ReplySaveRequestDto dto, @LoginUser SessionUser user) {
        return replyService.save(dto.setUser(user.toEntity()));
    }

    @PutMapping("/posts/reply/{replyNo}")
    public Long update(@PathVariable Long replyNo, @RequestBody ReplyUpdateRequestDto dto) {
        return replyService.update(replyNo, dto);
    }

    @DeleteMapping("/posts/reply/{replyNo}")
    public Long delete(@PathVariable Long replyNo) {
        replyService.delete(replyNo);

        return replyNo;
    }
}
