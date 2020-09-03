package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitment;
import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitmentRepository;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsListResponseDto;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsReadResponseDto;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudyService {
    private final BoardStudyRecruitmentRepository boardStudyRecruitmentRepository;

    @Transactional(readOnly = true)
    public List<StudyPostsListResponseDto> findAllDesc() {
        return boardStudyRecruitmentRepository.findAllByOrderByPostNoDesc().stream()
                .map(StudyPostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long save(StudyPostsSaveRequestDto requestDto) {
        return boardStudyRecruitmentRepository.save(requestDto.toEntity()).getPostNo();
    }

    @Transactional
    public StudyPostsReadResponseDto findById(Long postNo) {
        BoardStudyRecruitment entity = boardStudyRecruitmentRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당게시글이 없습니다. postNo=" + postNo));

        entity.addViewCount();

        return new StudyPostsReadResponseDto(entity);
    }
}
