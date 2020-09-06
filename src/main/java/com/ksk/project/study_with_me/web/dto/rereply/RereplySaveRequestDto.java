package com.ksk.project.study_with_me.web.dto.rereply;

import com.ksk.project.study_with_me.domain.reply.Reply;
import com.ksk.project.study_with_me.domain.rereply.Rereply;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Getter;

@Getter
public class RereplySaveRequestDto {
    private Long replyNo;
    private User user;
    private String content;
    private Long postNo;
    private String boardName;

    public RereplySaveRequestDto(Long replyNo, String content, Long postNo, String boardName) {
        this.replyNo = replyNo;
        this.content = content;
        this.postNo = postNo;
        this.boardName = boardName;
    }

    public RereplySaveRequestDto setUser(User user) {
        this.user = user;
        return this;
    }

    public Rereply toEntity() {
        return Rereply.builder()
                .reply(Reply.builder().replyNo(replyNo).build())
                .user(user)
                .content(content)
                .postNo(postNo)
                .boardName(boardName)
                .build();
    }
}
