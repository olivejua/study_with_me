package com.ksk.project.study_with_me.web.dto.place;

import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendation;
import com.ksk.project.study_with_me.domain.user.User;

import java.util.Arrays;
import java.util.List;

public class PlacePostsSaveRequestDto {
    private String title;
    private Long userCode;
    private String address;
    private String addressDetail;
    private String links;
    private boolean existThumbnail;
    private String content;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;

    public PlacePostsSaveRequestDto(Long userCode, String title, String address, String addressDetail,
                                    List<String> links, boolean existThumbnail, String content) {
        this.userCode = userCode;
        this.title = title;
        this.address = address;
        this.addressDetail = addressDetail;
        this.links = links.toString();
        this.existThumbnail = existThumbnail;
        this.content = content;
        this.likeCount = this.initialCount();
        this.dislikeCount = this.initialCount();
        this.viewCount = this.initialCount();

        System.out.println(Arrays.asList(this.links));
    }

    private int initialCount() {
        return 0;
    }

    public BoardPlaceRecommendation toEntity() {
        return BoardPlaceRecommendation.builder()
                .title(title)
                .user(User.builder().userCode(userCode).build())
                .address(address)
                .addressDetail(addressDetail)
                .content(content)
                .existThumbnail(existThumbnail)
                .links(links)
                .likeCount(likeCount)
                .dislikeCount(dislikeCount)
                .viewCount(viewCount)
                .build();
    }
}
