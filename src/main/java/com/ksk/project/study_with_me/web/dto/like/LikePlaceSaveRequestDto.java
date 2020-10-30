package com.ksk.project.study_with_me.web.dto.like;

import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendation;
import com.ksk.project.study_with_me.domain.like.IsLike;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LikePlaceSaveRequestDto {
    private Long userCode;
    private Long postNo;
    private boolean isLike;

    @Builder
    public LikePlaceSaveRequestDto(Long userCode, Long postNo, boolean isLike) {
        this.userCode = userCode;
        this.postNo = postNo;
        this.isLike = isLike;
    }

    public User getUserEntity() {
        return User.builder()
                .userCode(userCode)
                .build();
    }

    public BoardPlaceRecommendation getPlaceEntity() {
        return BoardPlaceRecommendation.builder()
                .postNo(postNo)
                .build();
    }

    public IsLike toEntity() {
        return IsLike.builder()
                .user(this.getUserEntity())
                .boardPlaceRecommendation(this.getPlaceEntity())
                .isLike(isLike)
                .build();
    }
}
