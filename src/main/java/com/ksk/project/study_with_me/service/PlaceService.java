package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendation;
import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendationRepository;
import com.ksk.project.study_with_me.domain.like.IsLikeRepository;
import com.ksk.project.study_with_me.web.dto.place.PostsListResponseDto;
import com.ksk.project.study_with_me.web.dto.place.PostsReadResponseDto;
import com.ksk.project.study_with_me.web.dto.place.PostsSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.place.PostsUpdateRequestDto;
import com.ksk.project.study_with_me.web.dto.study.SearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PlaceService {
    private final BoardPlaceRecommendationRepository placeRepository;
    private final IsLikeRepository likeRepository;

    @Transactional(readOnly = true)
    public Page<PostsListResponseDto> findPosts(Pageable pageable) {
        return placeRepository.findAll(pageable).map(PostsListResponseDto::new);
    }

    @Transactional(readOnly = true)
    public Page<PostsListResponseDto> searchPosts(Pageable pageable, SearchDto searchDto) {
        String keyword = searchDto.getKeyword();
        Page<BoardPlaceRecommendation> results = null;
        switch (searchDto.getSearchType()) {
            case "title" :
                results = placeRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
                break;
            case "address" :
                results = placeRepository.findByAddressContainingOrAddressDetailContaining(keyword, keyword, pageable);
                break;
            default:
                results = placeRepository.findAll(pageable);
                break;
        }

        return results.map(PostsListResponseDto::new);
    }

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return placeRepository.save(requestDto.toEntity()).getPostNo();
    }

    @Transactional
    public Long update(Long postNo, PostsUpdateRequestDto requestDto) {
        BoardPlaceRecommendation entity = placeRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postNo));

        entity.update(requestDto.getTitle(), requestDto.getAddress(), requestDto.getAddressDetail(), requestDto.getThumbnailPath()
                , requestDto.getContent(), requestDto.getLinks());

        return postNo;
    }

    @Transactional
    public String delete(Long postNo) {
        BoardPlaceRecommendation entity = placeRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postNo));

        likeRepository.deleteAllByBoardPlaceRecommendation(entity);
        placeRepository.delete(entity);

        return entity.getThumbnailPath();
    }

    @Transactional
    public PostsReadResponseDto findById(Long postNo) {
        BoardPlaceRecommendation entity = placeRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postNo=" + postNo));

        entity.addViewCount();

        return new PostsReadResponseDto(entity);
    }
}
