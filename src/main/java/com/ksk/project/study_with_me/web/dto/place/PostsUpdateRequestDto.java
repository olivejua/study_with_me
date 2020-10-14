package com.ksk.project.study_with_me.web.dto.place;

import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendation;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class PostsUpdateRequestDto {
    private String title;
    private String address;
    private String addressDetail;
    private String links;
    private String content;
    private String oldThumbnailPath;
    private String thumbnailPath;

    public PostsUpdateRequestDto(String title, String address, String addressDetail, List<String> links
            , String content, String oldThumbnailPath, String thumbnailPath) {
        this.title = title;
        this.address = address;
        this.addressDetail = addressDetail;
        this.links = links.toString();
        this.content = content;

        //thumbnailPath 있으면 rename
        //없으면 oldThumbnailPath로 대체

        this.oldThumbnailPath = oldThumbnailPath;
        this.thumbnailPath = thumbnailPath==null ? oldThumbnailPath : this.renameThumbnail(thumbnailPath);
    }

    private String renameThumbnail(String thumbnailName) {
        return "/resource/photo_upload/" + UUID.randomUUID().toString() + "." + thumbnailName.substring(thumbnailName.lastIndexOf(".")+1);
    }

    public BoardPlaceRecommendation toEntity() {
        return BoardPlaceRecommendation.builder()
                .title(title)
                .address(address)
                .addressDetail(addressDetail)
                .links(links)
                .thumbnailPath(thumbnailPath)
                .content(content)
                .build();
    }
}
