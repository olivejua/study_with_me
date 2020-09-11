package com.ksk.project.study_with_me.web.dto.study;

import com.ksk.project.study_with_me.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyPostsListResponseDto {
    private Long postNo;
    private User user;
    private String title;
    private int viewCount;
    private int replyCount;
//    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate;

//    public StudyPostsListResponseDto(BoardStudyRecruitment entity) {
//        this.postNo = entity.getPostNo();
//        this.user = entity.getUser();
//        this.title = entity.getTitle();
//        this.viewCount = entity.getViewCount();
//        this.replyCount = entity.getReplyCount();
//        this.modifiedDate = entity.getModifiedDate();
//    }

    public StudyPostsListResponseDto(Long postNo, User user, String title,
                                     int viewCount, int replyCount, LocalDateTime createdDate) {
        this.postNo = postNo;
        this.user = user;
        this.title = title;
        this.viewCount = viewCount;
        this.replyCount = replyCount;
        this.createdDate = createdDate;
    }
}
