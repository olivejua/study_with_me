package com.ksk.project.study_with_me.web.dto.study;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StudySearchListRequestDto {
    private String category;
    private String keyword;

    @Builder
    public StudySearchListRequestDto(String category, String keyword) {
        this.category = category;
        this.keyword = keyword;
    }
}
