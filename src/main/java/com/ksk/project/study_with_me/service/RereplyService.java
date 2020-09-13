package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.rereply.Rereply;
import com.ksk.project.study_with_me.domain.rereply.RereplyRepository;
import com.ksk.project.study_with_me.web.dto.rereply.RereplyListResponseDto;
import com.ksk.project.study_with_me.web.dto.rereply.RereplySaveRequestDto;
import com.ksk.project.study_with_me.web.dto.rereply.RereplyUpdateRequestDto;
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

    @Transactional(readOnly = true)
    public int countByPostNoAndBoardName(Long postNo, String boardName) {
        return rereplyRepository.countByPostNoAndBoardName(postNo, boardName);
    }

    @Transactional
    public void delete(Long rereplyNo) {
        Rereply entity = rereplyRepository.findById(rereplyNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 답글이 없습니다. rereplyNo=" + rereplyNo));

        rereplyRepository.delete(entity);
    }

    @Transactional
    public Long update(Long rereplyNo, RereplyUpdateRequestDto requestDto) {
        Rereply entity = rereplyRepository.findById(rereplyNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 답글이 없습니다. rereplyNo=" + rereplyNo));

        entity.update(requestDto.getContent());

        return rereplyNo;
    }
}
