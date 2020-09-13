package com.ksk.project.study_with_me.web.dto.rereply;

import lombok.Getter;

@Getter
public class RereplyUpdateRequestDto {
    private Long rereplyNo;
    private String content;

    public RereplyUpdateRequestDto(Long rereplyNo, String content) {
        this.rereplyNo = rereplyNo;
        this.content = content;
    }
}
