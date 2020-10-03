package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendation;
import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendationRepository;
import com.ksk.project.study_with_me.web.dto.place.PostsListResponseDto;
import com.ksk.project.study_with_me.web.dto.place.PostsReadResponseDto;
import com.ksk.project.study_with_me.web.dto.place.PostsSaveRequestDto;
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
    public Page<PostsListResponseDto> findPosts(Pageable pageable) {
        return placeRepository.findAll(pageable).map(entity -> new PostsListResponseDto(entity));
    }

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return placeRepository.save(requestDto.toEntity()).getPostNo();
    }

    @Transactional
    public PostsReadResponseDto findById(Long postNo) {
        BoardPlaceRecommendation entity = placeRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postNo=" + postNo));

        entity.addViewCount();

        return new PostsReadResponseDto(entity);
    }
}
