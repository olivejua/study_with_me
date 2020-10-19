package com.ksk.project.study_with_me.web.dto.study;

import lombok.Getter;

@Getter
public class SearchDto {
    private String searchType;
    private String keyword;

    public SearchDto(String searchType, String keyword) {
        this.searchType = searchType;
        this.keyword = keyword;
    }

    public boolean existSearch() {
        return searchType!=null && keyword!=null;
    }
}
