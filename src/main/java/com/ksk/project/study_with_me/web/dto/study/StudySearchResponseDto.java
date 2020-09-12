package com.ksk.project.study_with_me.web.dto.study;

import lombok.Getter;

@Getter
public class StudySearchResponseDto {
    private String searchType;
    private String keyword;

    public StudySearchResponseDto(String searchType, String keyword) {
        this.searchType = searchType;
        this.keyword = keyword;
    }
}
