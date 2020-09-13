package com.ksk.project.study_with_me.web.dto.place;

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

    public PlacePostsListResponseDto(Long postNo, User user, String title, String address, boolean existThumbnail,
                                     String links, int likeCount, int dislikeCount, int viewCount, LocalDateTime createdDate) {
        this.postNo = postNo;
        this.user = user;
        this.title = title;
        this.address = address;
        this.existThumbnail = existThumbnail;
        this.links = Arrays.asList(links.split(","));
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.viewCount = viewCount;
        this.createdDate = createdDate;
    }
}
