package com.ksk.project.study_with_me.web.dto.study;

import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
public class PostsUpdateRequestDto {
    private String title;
    private List<String> conditionLanguages;
    private String conditionPlace;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date conditionStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date conditionEndDate;
    private int conditionCapacity;
    private String conditionExplanation;

    @Builder
    public PostsUpdateRequestDto(String title, List<String> conditionLanguages, String conditionPlace, Date conditionStartDate
                                    , Date conditionEndDate, int conditionCapacity, String conditionExplanation) {
        this.title = title;
        this.conditionLanguages = conditionLanguages;
        this.conditionPlace = conditionPlace;
        this.conditionCapacity = conditionCapacity;
        this.conditionStartDate = conditionStartDate;
        this.conditionEndDate = conditionEndDate;
        this.conditionExplanation = conditionExplanation;
    }
}
