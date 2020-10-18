package com.ksk.project.study_with_me.web.dto.study;

import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitment;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
public class StudyPostsUpdateRequestDto {
    private Long userCode;
    private String title;
    private String conditionLanguages;
    private String conditionPlace;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date conditionStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date conditionEndDate;
    private int conditionCapacity;
    private String conditionExplanation;
    private int viewCount;
    private int commentCount;

    @Builder
    public StudyPostsUpdateRequestDto(Long userCode, String title, String conditionLanguages, String conditionPlace, Date conditionStartDate
                                    , Date conditionEndDate, int conditionCapacity, String conditionExplanation) {
        this.userCode = userCode;
        this.title = title;
        this.conditionLanguages = conditionLanguages;
        this.conditionPlace = conditionPlace;
        this.conditionStartDate = conditionStartDate;
        this.conditionEndDate = conditionEndDate;
        this.conditionCapacity = conditionCapacity;
        this.conditionExplanation = conditionExplanation;
        this.viewCount = setInitialNumber();
        this.commentCount = setInitialNumber();
    }

    private int setInitialNumber() {
        return 0;
    }

    public BoardStudyRecruitment toEntity() {
        return BoardStudyRecruitment.builder()
                .user(User.builder().userCode(userCode).build())
                .title(title)
                .conditionLanguages(conditionLanguages)
                .conditionPlace(conditionPlace)
                .conditionStartDate(conditionStartDate)
                .conditionEndDate(conditionEndDate)
                .conditionCapacity(conditionCapacity)
                .conditionExplanation(conditionExplanation)
                .viewCount(viewCount)
                .commentCount(commentCount)
                .build();
    }
}
