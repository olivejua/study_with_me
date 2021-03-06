package com.ksk.project.study_with_me.domain.boardPlaceRecommendation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardPlaceRecommendationRepository
        extends JpaRepository<BoardPlaceRecommendation, Long> {

    Page<BoardPlaceRecommendation> findAll(Pageable pageable);

    Page<BoardPlaceRecommendation> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    Page<BoardPlaceRecommendation> findByAddressContainingOrAddressDetailContaining(String address, String addressDetail, Pageable pageable);
}
