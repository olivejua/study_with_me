package com.ksk.project.study_with_me.web.dto.reply;

import com.ksk.project.study_with_me.domain.reply.Reply;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Getter;

@Getter
public class ReplySaveRequestDto {
    private String boardName;
    private Long postNo;
    private User user;
    private String content;

    public ReplySaveRequestDto(String boardName, Long postNo, String content) {
        this.boardName = boardName;
        this.postNo = postNo;
        this.content = content;
    }

    public ReplySaveRequestDto setUser(User user) {
        this.user = user;
        return this;
    }

    public Reply toEntity() {
        return Reply.builder()
                .boardName(boardName)
                .postNo(postNo)
                .user(user)
                .content(content)
                .build();
    }
}
