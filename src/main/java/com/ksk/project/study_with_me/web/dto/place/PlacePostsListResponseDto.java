package com.ksk.project.study_with_me.web.dto.place;

import com.ksk.project.study_with_me.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;
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

}
