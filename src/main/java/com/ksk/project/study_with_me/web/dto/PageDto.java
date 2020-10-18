package com.ksk.project.study_with_me.web.dto;

import com.ksk.project.study_with_me.web.dto.study.SearchDto;
import lombok.Getter;

@Getter
public class PageDto {
    private int page;
    private SearchDto search;

    public PageDto(int page, String searchType, String keyword) {
        this.page = page;
        this.search = new SearchDto(searchType, keyword);
    }
}
