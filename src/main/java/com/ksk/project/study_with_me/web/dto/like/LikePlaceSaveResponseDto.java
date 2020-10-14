package com.ksk.project.study_with_me.web.dto.like;

import lombok.Getter;

@Getter
public class LikePlaceSaveResponseDto {
    private int likeCount;
    private int dislikeCount;

    public LikePlaceSaveResponseDto(int likeCount, int dislikeCount) {
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }
}
