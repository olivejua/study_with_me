package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.boardStudyRecruitment.BoardStudyRecruitmentRepository;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsListResponseDto;
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
}
