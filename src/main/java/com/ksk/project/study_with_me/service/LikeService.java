package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.like.IsLike;
import com.ksk.project.study_with_me.domain.like.IsLikeRepository;
import com.ksk.project.study_with_me.web.dto.place.PostsReadResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final IsLikeRepository likeRepository;

    @Transactional(readOnly = true)
    public PostsReadResponseDto findByUserAndPost(PostsReadResponseDto dto) {
        IsLike entity = likeRepository.findByUserAndBoardPlaceRecommendation(dto.getUser(), dto.toEntity());
        return dto.setLike(entity);
    }
}
