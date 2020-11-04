package com.ksk.project.study_with_me.web.dto.place;

import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendation;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class PostsSaveRequestDto {
    private String title;
    private Long userCode;
    private String address;
    private String addressDetail;
    private List<String> links;
    private String thumbnailPath;
    private String content;
    private int likeCount;
    private int dislikeCount;
    private int viewCount;

    @Builder
    public PostsSaveRequestDto(Long userCode, String title, String address, String addressDetail,
                               List<String> links, String thumbnailPath, String content) {
        this.userCode = userCode;
        this.title = title;
        this.address = address;
        this.addressDetail = addressDetail;
        this.links = links;
        this.thumbnailPath = this.renameThumbnail(thumbnailPath);
        this.content = content;
        this.likeCount = this.initialCount();
        this.dislikeCount = this.initialCount();
        this.viewCount = this.initialCount();
    }

    private int initialCount() {
        return 0;
    }

    private String renameThumbnail(String thumbnailName) {
        return "/resource/photo_upload/" + UUID.randomUUID().toString() + "." + thumbnailName.substring(thumbnailName.lastIndexOf(".")+1);
    }

    public BoardPlaceRecommendation toEntity() {
        return BoardPlaceRecommendation.builder()
                .title(title)
                .user(User.builder().userCode(userCode).build())
                .address(address)
                .addressDetail(addressDetail)
                .content(content)
                .thumbnailPath(thumbnailPath)
                .links(links.toString())
                .likeCount(likeCount)
                .dislikeCount(dislikeCount)
                .viewCount(viewCount)
                .build();
    }
}
