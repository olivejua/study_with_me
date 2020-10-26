package com.ksk.project.study_with_me.web.dto.question;

import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestion;
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

    public PostsListResponseDto(BoardQuestion entity) {
        this.postNo = entity.getPostNo();
        this.user = entity.getUser();
        this.title = entity.getTitle();
        this.viewCount = entity.getViewCount();
        this.commentCount = entity.getCommentCount();
        this.createdDate = entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
