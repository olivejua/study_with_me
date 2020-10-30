package com.ksk.project.study_with_me.web.dto.reply;

import com.ksk.project.study_with_me.domain.reply.Reply;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReplySaveRequestDto {
    private Long commentNo;
    private User user;
    private String content;
    private Long postNo;
    private String boardName;

    @Builder
    public ReplySaveRequestDto(Long commentNo, String content, Long postNo, String boardName) {
        this.commentNo = commentNo;
        this.content = content;
        this.postNo = postNo;
        this.boardName = boardName;
    }

    public ReplySaveRequestDto setUser(User user) {
        this.user = user;
        return this;
    }

    public Reply toEntity() {
        return Reply.builder()
//                .comment(Comment.builder().commentNo(commentNo).build())
                .commentNo(commentNo)
                .user(user)
                .content(content)
                .postNo(postNo)
                .boardName(boardName)
                .build();
    }
}
