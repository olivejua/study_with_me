package com.ksk.project.study_with_me.web.dto.study;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitment;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.web.dto.PageDto;
import lombok.Getter;

import java.util.Date;

@Getter
public class PostsReadResponseDto {
    private Long postNo;
    private String boardName;
    private User user;
    private String title;
    private String conditionLanguages;
    private String conditionPlace;
    private Date conditionStartDate;
    private Date conditionEndDate;
    private int conditionCapacity;
    private String conditionExplanation;
    private int viewCount;
    private int commentCount;
    private PageDto pageInfo;

    public PostsReadResponseDto(BoardStudyRecruitment entity) {
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
        this.commentCount = entity.getCommentCount();
        this.boardName = MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getShortName();
    }

    public PostsReadResponseDto savePageInfo(PageDto pageInfo) {
        this.pageInfo = pageInfo;
        return this;
    }
}
