package com.ksk.project.study_with_me.web.dto.reply;

import com.ksk.project.study_with_me.domain.reply.Reply;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Getter;

import java.time.ZoneId;
import java.util.Date;

@Getter
public class ReplyListResponseDto {
    private Long replyNo;
    private String boardName;
    private Long postNo;
    private User user;
    private String content;
    private Date modifiedDate;

    public ReplyListResponseDto(Reply entity) {
        this.replyNo = entity.getReplyNo();
        this.boardName = entity.getBoardName();
        this.postNo = entity.getPostNo();
        this.user = entity.getUser();
        this.content = entity.getContent();
        this.modifiedDate = Date.from(entity.getModifiedDate().atZone(ZoneId.systemDefault()).toInstant());
    }
}
