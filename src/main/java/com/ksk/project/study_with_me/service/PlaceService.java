package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendationRepository;
import com.ksk.project.study_with_me.web.dto.place.PlacePostsListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PlaceService {
    private final BoardPlaceRecommendationRepository placeRepository;

    @Transactional(readOnly = true)
    public Page<PlacePostsListResponseDto> findPosts(Pageable pageable) {
        return placeRepository.findAll(pageable).map(entity -> new PlacePostsListResponseDto(entity));
    }
}
