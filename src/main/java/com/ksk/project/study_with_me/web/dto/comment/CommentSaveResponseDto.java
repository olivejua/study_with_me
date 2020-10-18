package com.ksk.project.study_with_me.web.dto.comment;

import com.ksk.project.study_with_me.domain.comment.Comment;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentSaveResponseDto {
    private Long commentNo;
    private User user;
    private String content;
    private LocalDateTime modifiedDate;

    public CommentSaveResponseDto(Comment entity) {
        this.commentNo = entity.getCommentNo();
        this.user = entity.getUser();
        this.content = entity.getContent();
        this.modifiedDate = entity.getModifiedDate();
    }
}
