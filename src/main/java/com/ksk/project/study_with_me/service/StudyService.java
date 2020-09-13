package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitment;
import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitmentRepository;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsListResponseDto;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsReadResponseDto;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StudyService {
    private final BoardStudyRecruitmentRepository boardStudyRecruitmentRepository;
    private final ReplyService replyService;
    private final RereplyService rereplyService;

    @Transactional(readOnly = true)
    public Page<StudyPostsListResponseDto> findPosts(Pageable pageable) {
        Page<BoardStudyRecruitment> posts = boardStudyRecruitmentRepository.findAll(pageable);

        String boardName = MatchNames.Boards.BOARD_STUDY_RECRUITMENT.getShortName();
        return posts.map(entity -> {
            int replyCount = replyService.countByPostNoAndBoardName(entity.getPostNo(), boardName) +
                    rereplyService.countByPostNoAndBoardName(entity.getPostNo(), boardName);

            entity.updateReplyCount(replyCount);

            return new StudyPostsListResponseDto(
                    entity.getPostNo(), entity.getUser(),
                    entity.getTitle(), entity.getViewCount(),
                    entity.getReplyCount(), entity.getCreatedDate()
            );});
    }

    @Transactional(readOnly = true)
    public Page<StudyPostsListResponseDto> searchPosts(Pageable pageable, String searchType, String keyword) {
        Page<BoardStudyRecruitment> results = null;
        switch (searchType) {
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

        return results.map(entity -> new StudyPostsListResponseDto(
                        entity.getPostNo(), entity.getUser(),
                        entity.getTitle(), entity.getViewCount(),
                        entity.getReplyCount(), entity.getCreatedDate()
                ));
    }

    @Transactional
    public Long save(StudyPostsSaveRequestDto requestDto) {
        return boardStudyRecruitmentRepository.save(requestDto.toEntity()).getPostNo();
    }

    @Transactional
    public Long update(Long postNo, StudyPostsUpdateRequestDto requestDto) {
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
    public StudyPostsReadResponseDto findById(Long postNo) {
        BoardStudyRecruitment entity = boardStudyRecruitmentRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당게시글이 없습니다. postNo=" + postNo));

        entity.addViewCount();

        return new StudyPostsReadResponseDto(entity);
    }
}
