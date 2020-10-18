package com.ksk.project.study_with_me.web.dto.comment;

import com.ksk.project.study_with_me.domain.comment.Comment;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.web.dto.reply.ReplyListResponseDto;
import lombok.Getter;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Getter
public class CommentListResponseDto {
    private Long commentNo;
    private String boardName;
    private Long postNo;
    private User user;
    private String content;
    private Date modifiedDate;
    private List<ReplyListResponseDto> replies;

    public CommentListResponseDto(Comment entity, List<ReplyListResponseDto> replies) {
        this.commentNo = entity.getCommentNo();
        this.boardName = entity.getBoardName();
        this.postNo = entity.getPostNo();
        this.user = entity.getUser();
        this.content = entity.getContent();
        this.modifiedDate = Date.from(entity.getModifiedDate().atZone(ZoneId.systemDefault()).toInstant());
        this.replies = replies;
    }
}
