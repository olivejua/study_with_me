package com.ksk.project.study_with_me.web.dto.reply;

import lombok.Getter;

@Getter
public class ReplyUpdateRequestDto {
    private Long replyNo;
    private String content;

    public ReplyUpdateRequestDto(Long replyNo, String content) {
        this.replyNo = replyNo;
        this.content = content;
    }
}
