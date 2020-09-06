package com.ksk.project.study_with_me.web.dto.study;

import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitment;
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
    private LocalDateTime modifiedDate;

    public StudyPostsListResponseDto(BoardStudyRecruitment entity) {
        this.postNo = entity.getPostNo();
        this.user = entity.getUser();
        this.title = entity.getTitle();
        this.viewCount = entity.getViewCount();
        this.replyCount = entity.getReplyCount();
        this.modifiedDate = entity.getModifiedDate();
    }
}
