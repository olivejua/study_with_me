package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitment;
import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitmentRepository;
import com.ksk.project.study_with_me.web.dto.study.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StudyService {
    private final BoardStudyRecruitmentRepository boardStudyRecruitmentRepository;
    private final CommentService commentService;
    private final ReplyService replyService;

    @Transactional(readOnly = true)
    public Page<PostsListResponseDto> findPosts(Pageable pageable) {
        Page<BoardStudyRecruitment> posts = boardStudyRecruitmentRepository.findAll(pageable);

        String boardName = MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getShortName();
        return posts.map(entity -> {
            int commentCount = commentService.countByPostNoAndBoardName(entity.getPostNo(), boardName) +
                    replyService.countByPostNoAndBoardName(entity.getPostNo(), boardName);

            entity.updateCommentCount(commentCount);

            return new PostsListResponseDto(
                    entity.getPostNo(), entity.getUser(),
                    entity.getTitle(), entity.getViewCount(),
                    entity.getCommentCount(), entity.getCreatedDate()
            );});
    }

    @Transactional(readOnly = true)
    public Page<PostsListResponseDto> searchPosts(Pageable pageable, SearchDto searchDto) {
        String keyword = searchDto.getKeyword();
        Page<BoardStudyRecruitment> results = null;
        switch (searchDto.getSearchType()) {
            case "title" :
                results = boardStudyRecruitmentRepository.findByTitleContaining(keyword, pageable);
                break;
            case "conditionLanguages" :
                results = boardStudyRecruitmentRepository.findByConditionLanguagesContaining(keyword, pageable);
                break;
            case "conditionPlace" :
                results = boardStudyRecruitmentRepository.findByConditionPlaceContaining(keyword, pageable);
                break;
            case "conditionCapacity" :
                results = boardStudyRecruitmentRepository.findByConditionCapacityContaining(keyword, pageable);
                break;
            case "conditionExplanation" :
                results = boardStudyRecruitmentRepository.findByConditionExplanationContaining(keyword, pageable);
                break;
            default:
                results = boardStudyRecruitmentRepository.findAll(pageable);
                break;
        }

        return results.map(entity -> new PostsListResponseDto(
                        entity.getPostNo(), entity.getUser(),
                        entity.getTitle(), entity.getViewCount(),
                        entity.getCommentCount(), entity.getCreatedDate()
                ));
    }

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return boardStudyRecruitmentRepository.save(requestDto.toEntity()).getPostNo();
    }

    @Transactional
    public Long update(Long postNo, PostsUpdateRequestDto requestDto) {
        BoardStudyRecruitment entity = boardStudyRecruitmentRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postNo));

        entity.update(requestDto.getTitle(), requestDto.getConditionLanguages(), requestDto.getConditionPlace(), requestDto.getConditionStartDate(),
                requestDto.getConditionEndDate(), requestDto.getConditionCapacity(), requestDto.getConditionExplanation());

        return postNo;
    }

    @Transactional
    public void delete(Long postNo) {
        BoardStudyRecruitment entity = boardStudyRecruitmentRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postNo=" + postNo));

        boardStudyRecruitmentRepository.delete(entity);
    }

    @Transactional
    public PostsReadResponseDto findById(Long postNo) {
        BoardStudyRecruitment entity = boardStudyRecruitmentRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당게시글이 없습니다. postNo=" + postNo));

        entity.addViewCount();

        return new PostsReadResponseDto(entity);
    }
}
