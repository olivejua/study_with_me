package com.ksk.project.study_with_me.web.dto.place;

import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendation;
import com.ksk.project.study_with_me.domain.like.IsLike;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class PostsReadResponseDto {
    private Long postNo;
    private User user;
    private String title;
    private String address;
    private String addressDetail;
    private String thumbnailPath;
    private String content;
    private List<String> links;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;
    private IsLike like;

    public PostsReadResponseDto(BoardPlaceRecommendation entity) {
        this.postNo = entity.getPostNo();
        this.user = entity.getUser();
        this.title = entity.getTitle();
        this.address = entity.getAddress();
        this.addressDetail = entity.getAddressDetail();
        this.thumbnailPath = entity.getThumbnailPath();
        this.content = entity.getContent();
        this.links = Arrays.asList(entity.getLinks().replace("[","").replace("]","").split(","));
        this.likeCount = entity.getLikeCount();
        this.dislikeCount = entity.getDislikeCount();
        this.viewCount = entity.getViewCount();
    }

    public PostsReadResponseDto setLike(IsLike like) {
        this.like = like;

        return this;
    }

    public BoardPlaceRecommendation toEntity() {
        return BoardPlaceRecommendation.builder()
                .postNo(postNo)
                .user(user)
                .title(title)
                .address(address)
                .addressDetail(addressDetail)
                .links(links.toString())
                .thumbnailPath(thumbnailPath)
                .content(content)
                .likeCount(likeCount)
                .dislikeCount(dislikeCount)
                .viewCount(viewCount)
                .build();
    }
}
