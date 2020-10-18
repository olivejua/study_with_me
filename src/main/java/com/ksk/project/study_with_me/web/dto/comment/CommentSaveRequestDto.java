package com.ksk.project.study_with_me.web.dto.comment;

import com.ksk.project.study_with_me.domain.comment.Comment;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Getter;

@Getter
public class CommentSaveRequestDto {
    private String boardName;
    private Long postNo;
    private Long userCode;
    private String content;

    public CommentSaveRequestDto(String boardName, Long postNo, Long userCode, String content) {
        this.boardName = boardName;
        this.postNo = postNo;
        this.userCode = userCode;
        this.content = content;
    }

    public Comment toEntity() {
        return Comment.builder()
                .boardName(boardName)
                .postNo(postNo)
                .user(User.builder().userCode(userCode).build())
                .content(content)
                .build();
    }
}
