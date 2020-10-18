package com.ksk.project.study_with_me.web.dto.question;

import com.ksk.project.study_with_me.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long postNo;
    private User user;
    private String title;
    private int viewCount;
    private int commentCount;
    private LocalDateTime createdDate;

    public PostsListResponseDto(Long postNo, User user, String title,
                                int viewCount, int commentCount, LocalDateTime createdDate) {
        this.postNo = postNo;
        this.user = user;
        this.title = title;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.createdDate = createdDate;
    }
}
