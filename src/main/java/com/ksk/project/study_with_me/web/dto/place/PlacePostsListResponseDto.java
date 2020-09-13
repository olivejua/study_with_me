package com.ksk.project.study_with_me.web.dto.place;

import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendation;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
public class PlacePostsListResponseDto {
    private Long postNo;
    private User user;
    private String title;
    private String address;
    private boolean existThumbnail;
    private List<String> links;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;
    private LocalDateTime createdDate;

    public PlacePostsListResponseDto(BoardPlaceRecommendation entity) {
        this.postNo = entity.getPostNo();
        this.user = entity.getUser();
        this.title = entity.getTitle();
        this.address = entity.getAddress();
        this.existThumbnail = entity.isExistThumbnail();
        this.links = Arrays.asList(entity.getLinks().split(","));
        this.likeCount = entity.getLikeCount();
        this.dislikeCount = entity.getDislikeCount();
        this.viewCount = entity.getViewCount();
        this.createdDate = entity.getCreatedDate();
    }
}
