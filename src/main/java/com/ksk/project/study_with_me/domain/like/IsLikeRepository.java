package com.ksk.project.study_with_me.domain.like;

import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendation;
import com.ksk.project.study_with_me.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IsLikeRepository extends JpaRepository<IsLike, IsLikePK> {

    public IsLike findByUserAndBoardPlaceRecommendation(User user, BoardPlaceRecommendation post);
}
