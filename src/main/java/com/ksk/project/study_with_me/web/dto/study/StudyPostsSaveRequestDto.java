package com.ksk.project.study_with_me.web.dto.study;

import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitment;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
public class StudyPostsSaveRequestDto {
    private Long userCode;
    private User user;
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
    private int replyCount;

    public StudyPostsSaveRequestDto(Long userCode, String title, List<String> conditionLanguages, String conditionPlace, Date conditionStartDate
                                    , Date conditionEndDate, int conditionCapacity, String conditionExplanation, User user) {
        this.userCode = userCode;
        this.user = user;
        this.title = title;
        this.conditionLanguages = conditionLanguages.toString();
        this.conditionPlace = conditionPlace;
        this.conditionStartDate = conditionStartDate;
        this.conditionEndDate = conditionEndDate;
        this.conditionCapacity = conditionCapacity;
        this.conditionExplanation = conditionExplanation;
        this.viewCount = setInitialNumber();
        this.replyCount = setInitialNumber();
    }

    private int setInitialNumber() {
        return 0;
    }

    public BoardStudyRecruitment toEntity() {
        return BoardStudyRecruitment.builder()
                .user(user)
                .title(title)
//                .conditionLanguages(conditionLanguages)
                .conditionPlace(conditionPlace)
                .conditionStartDate(conditionStartDate)
                .conditionEndDate(conditionEndDate)
                .conditionCapacity(conditionCapacity)
                .conditionExplanation(conditionExplanation)
                .viewCount(viewCount)
                .replyCount(replyCount)
                .build();
    }
}
