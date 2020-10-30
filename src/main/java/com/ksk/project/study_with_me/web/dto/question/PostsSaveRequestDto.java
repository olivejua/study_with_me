package com.ksk.project.study_with_me.web.dto.question;

import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestion;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostsSaveRequestDto {
    private Long userCode;
    private String title;
    private String content;
    private int viewCount;
    private int commentCount;

    @Builder
    public PostsSaveRequestDto(Long userCode, String title, String content) {
        this.userCode = userCode;
        this.title = title;
        this.content = content;
        this.viewCount = initialCount();
        this.commentCount = initialCount();
    }

    private int initialCount() {
        return 0;
    }

    public BoardQuestion toEntity() {
        return BoardQuestion.builder()
                .user(User.builder().userCode(userCode).build())
                .title(title)
                .content(content)
                .viewCount(viewCount)
                .commentCount(commentCount)
                .build();
    }
}
