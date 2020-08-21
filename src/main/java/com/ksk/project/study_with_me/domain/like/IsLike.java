package com.ksk.project.study_with_me.domain.like;

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
    private Long userCode;

    @Id
    private Long postNo;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isLike;

    @Builder
    public IsLike(Long userCode, Long postNo, boolean isLike) {
        this.userCode = userCode;
        this.postNo = postNo;
        this.isLike = isLike;
    }
}
