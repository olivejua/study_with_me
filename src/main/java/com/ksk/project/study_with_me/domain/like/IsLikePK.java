package com.ksk.project.study_with_me.domain.like;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class IsLikePK implements Serializable {
//    private static final long serialVersionUID = 1L;

    private Long user;
    private Long boardPlaceRecommendation;
}
