package com.ksk.project.study_with_me.web.dto.study;

import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitment;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Getter;

import java.util.Date;

@Getter
public class StudyPostsReadResponseDto {
    private Long postNo;
    private User user;
    private String title;
    private String conditionLanguages;
    private String conditionPlace;
    private Date conditionStartDate;
    private Date conditionEndDate;
    private int conditionCapacity;
    private String conditionExplanation;
    private int viewCount;
    private int replyCount;

    public StudyPostsReadResponseDto(BoardStudyRecruitment entity) {
        this.postNo = entity.getPostNo();
        this.user = entity.getUser();
        this.title = entity.getTitle();
        this.conditionLanguages = entity.getConditionLanguages();
        this.conditionPlace = entity.getConditionPlace();
        this.conditionStartDate = entity.getConditionStartDate();
        this.conditionEndDate = entity.getConditionEndDate();
        this.conditionCapacity = entity.getConditionCapacity();
        this.conditionExplanation = entity.getConditionExplanation();
        this.viewCount = entity.getViewCount();
        this.replyCount = entity.getReplyCount();
    }
}
