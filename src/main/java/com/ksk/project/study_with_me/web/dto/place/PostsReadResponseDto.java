package com.ksk.project.study_with_me.web.dto.place;

import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendation;
import com.ksk.project.study_with_me.domain.like.IsLike;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.web.dto.PageDto;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
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
    private int existLike;
    private int like;
    private String createdDate;
    private PageDto pageInfo;

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
        this.createdDate = entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

    }

    public PostsReadResponseDto savePageInfo(PageDto pageInfo) {
        this.pageInfo = pageInfo;
        return this;
    }

    public PostsReadResponseDto setLike(IsLike like) {
        if(like == null) {
            return this;
        }

        this.existLike = 1; //true
        this.like = like.isLike() ? 1 : 0;

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
