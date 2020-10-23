package com.ksk.project.study_with_me.web.dto.study;

import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitment;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class PostsListResponseDto {
    private Long postNo;
    private User user;
    private String title;
    private int viewCount;
    private int commentCount;
    private String createdDate;

    public PostsListResponseDto(BoardStudyRecruitment entity) {
        this.postNo = entity.getPostNo();
        this.user = entity.getUser();
        this.title = entity.getTitle();
        this.viewCount = entity.getViewCount();
        this.commentCount = entity.getCommentCount();
        this.createdDate = entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
