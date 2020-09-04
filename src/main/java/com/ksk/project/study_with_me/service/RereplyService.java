package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.rereply.RereplyRepository;
import com.ksk.project.study_with_me.web.dto.rereply.RereplyListResponseDto;
import com.ksk.project.study_with_me.web.dto.rereply.RereplySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RereplyService {
    private final RereplyRepository rereplyRepository;

    @Transactional
    public Long save(RereplySaveRequestDto requestDto) {
        return rereplyRepository.save(requestDto.toEntity()).getRereplyNo();
    }

    @Transactional(readOnly = true)
    public List<RereplyListResponseDto> findAllByPostNoAndBoardName(Long postNo, String boardName) {
        return rereplyRepository.findByPostNoAndBoardNameOrderByReplyAsc(postNo, boardName).stream()
                .map(RereplyListResponseDto::new)
                .collect(Collectors.toList());
    }
}
