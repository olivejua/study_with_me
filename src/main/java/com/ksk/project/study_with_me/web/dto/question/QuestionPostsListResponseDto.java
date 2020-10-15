package com.ksk.project.study_with_me.web.dto.question;

import com.ksk.project.study_with_me.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuestionPostsListResponseDto {
    private Long postNo;
    private User user;
    private String title;
    private int viewCount;
    private int replyCount;
    private LocalDateTime createdDate;

    public QuestionPostsListResponseDto(Long postNo, User user, String title,
                                        int viewCount, int replyCount, LocalDateTime createdDate) {
        this.postNo = postNo;
        this.user = user;
        this.title = title;
        this.viewCount = viewCount;
        this.replyCount = replyCount;
        this.createdDate = createdDate;
    }
}
