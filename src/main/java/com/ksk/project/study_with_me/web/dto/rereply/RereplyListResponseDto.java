package com.ksk.project.study_with_me.web.dto.rereply;

import com.ksk.project.study_with_me.domain.reply.Reply;
import com.ksk.project.study_with_me.domain.rereply.Rereply;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Getter;

import java.time.ZoneId;
import java.util.Date;

@Getter
public class RereplyListResponseDto {
    private Long rereplyNo;
    private Reply reply;
    private User user;
    private String content;
    private Date modifiedDate;

    public RereplyListResponseDto(Rereply entity) {
        this.rereplyNo = entity.getRereplyNo();
        this.reply = entity.getReply();
        this.user = entity.getUser();
        this.content = entity.getContent();
        this.modifiedDate = Date.from(entity.getModifiedDate().atZone(ZoneId.systemDefault()).toInstant());
    }
}
