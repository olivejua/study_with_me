package com.ksk.project.study_with_me.web.dto.study;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitment;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.web.dto.PageDto;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
public class PostsReadResponseDto {
    private Long postNo;
    private String boardName;
    private User user;
    private String title;
    private List<String> conditionLanguages;
    private String conditionPlace;
    private Date conditionStartDate;
    private Date conditionEndDate;
    private int conditionCapacity;
    private String conditionExplanation;
    private int viewCount;
    private int commentCount;
    private String createdDate;
    private PageDto pageInfo;

    public PostsReadResponseDto(BoardStudyRecruitment entity) {
        this.postNo = entity.getPostNo();
        this.user = entity.getUser();
        this.title = entity.getTitle();
        this.conditionLanguages = Arrays.asList(entity.getConditionLanguages().replace("[","").replace("]","").split(","));
        this.conditionPlace = entity.getConditionPlace();
        this.conditionStartDate = entity.getConditionStartDate();
        this.conditionEndDate = entity.getConditionEndDate();
        this.conditionCapacity = entity.getConditionCapacity();
        this.conditionExplanation = entity.getConditionExplanation();
        this.viewCount = entity.getViewCount();
        this.commentCount = entity.getCommentCount();
        this.createdDate = entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.boardName = MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getShortName();
    }

    public PostsReadResponseDto savePageInfo(PageDto pageInfo) {
        this.pageInfo = pageInfo;
        return this;
    }
}
