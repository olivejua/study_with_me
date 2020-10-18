package com.ksk.project.study_with_me.web.dto.question;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestion;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Getter;

@Getter
public class PostsReadResponseDto {
    private Long postNo;
    private String boardName;
    private User user;
    private String title;
    private String content;
    private int viewCount;
    private int commentCount;

    public PostsReadResponseDto(BoardQuestion entity) {
        this.postNo = entity.getPostNo();
        this.user = entity.getUser();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.viewCount = entity.getViewCount();
        this.commentCount = entity.getCommentCount();
        this.boardName = MatchNames.Boards.BOARD_QUESTION.getShortName();
    }
}
