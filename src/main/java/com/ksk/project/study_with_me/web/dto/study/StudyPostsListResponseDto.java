package com.ksk.project.study_with_me.web.dto.study;

import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StudyPostsListResponseDto {
    private Long postNo;
    private String userName;
    private String title;
    private int viewCount;
    private int replyCount;
    private LocalDateTime modifiedDate;

    public StudyPostsListResponseDto(BoardStudyRecruitment entity) {
        this.postNo = entity.getPostNo();
        this.userName = entity.getUser().getName();
        this.title = getTitle();
        this.viewCount = entity.getViewCount();
        this.replyCount = entity.getReplyCount();
        this.modifiedDate = entity.getModifiedDate();
    }
}
