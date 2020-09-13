package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlaceService {
    private final BoardPlaceRecommendationRepository placeRepository;
}
