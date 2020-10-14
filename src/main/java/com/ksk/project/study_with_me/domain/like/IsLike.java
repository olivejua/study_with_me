package com.ksk.project.study_with_me.domain.like;

import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendation;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@IdClass(IsLikePK.class)
@Entity
public class IsLike {

    @Id
    @ManyToOne
    @JoinColumn(name = "userCode")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "postNo")
    private BoardPlaceRecommendation boardPlaceRecommendation;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isLike;

    @Builder
    public IsLike(User user, BoardPlaceRecommendation boardPlaceRecommendation, boolean isLike) {
        this.user = user;
        this.boardPlaceRecommendation = boardPlaceRecommendation;
        this.isLike = isLike;
    }

    public void update(boolean isLike) {
        this.isLike = isLike;
    }
}
