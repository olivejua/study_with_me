package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendation;
import com.ksk.project.study_with_me.domain.boardPlaceRecommendation.BoardPlaceRecommendationRepository;
import com.ksk.project.study_with_me.domain.like.IsLike;
import com.ksk.project.study_with_me.domain.like.IsLikeRepository;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.web.dto.like.LikePlaceSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.like.LikePlaceSaveResponseDto;
import com.ksk.project.study_with_me.web.dto.place.PostsReadResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final IsLikeRepository likeRepository;
    private final BoardPlaceRecommendationRepository placeRepository;

    @Transactional(readOnly = true)
    public PostsReadResponseDto findByUserAndPost(PostsReadResponseDto dto, SessionUser user) {
        IsLike entity = likeRepository.findByUserAndBoardPlaceRecommendation(user.toEntity(), dto.toEntity());
        return dto.setLike(entity);
    }

    @Transactional
    public LikePlaceSaveResponseDto saveOrUpdate(LikePlaceSaveRequestDto requestDto) {
        IsLike likeEntity = likeRepository.findByUserAndBoardPlaceRecommendation(requestDto.getUserEntity(), requestDto.getPlaceEntity());
        BoardPlaceRecommendation postEntity = placeRepository.findById(requestDto.getPostNo())
                .orElseThrow(IllegalArgumentException::new);

        if(likeEntity == null) {
            likeRepository.save(requestDto.toEntity());
        } else {
            if(likeEntity.isLike()) {
                postEntity.updateLikeCount(-1);
            } else {
                postEntity.updateDislikeCount(-1);
            }

            likeEntity.update(requestDto.isLike());
        }

        if (requestDto.isLike()) {
            postEntity.updateLikeCount(1);
        } else {
            postEntity.updateDislikeCount(1);
        }

        return new LikePlaceSaveResponseDto(postEntity.getLikeCount(), postEntity.getDislikeCount());
    }

    @Transactional
    public LikePlaceSaveResponseDto delete(Long postNo, Long userCode) {
        IsLike likeEntity = likeRepository.findByUserAndBoardPlaceRecommendation(
                User.builder().userCode(userCode).build(), BoardPlaceRecommendation.builder().postNo(postNo).build());
        BoardPlaceRecommendation postEntity = likeEntity.getBoardPlaceRecommendation();

        if(likeEntity.isLike()) {
            postEntity.updateLikeCount(-1);
        } else {
            postEntity.updateDislikeCount(-1);
        }

        likeRepository.delete(likeEntity);

        return new LikePlaceSaveResponseDto(postEntity.getLikeCount(), postEntity.getDislikeCount());
    }
}
