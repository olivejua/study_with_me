package com.ksk.project.study_with_me.web.dto.place;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostsSaveResponseDto {
    private Long postNo;
    private String thumbnailName;

    @Builder
    public PostsSaveResponseDto(Long postNo, String thumbnailPath) {
        this.postNo = postNo;
        this.thumbnailName = thumbnailPath.substring(thumbnailPath.lastIndexOf("/")+1);
    }
}
