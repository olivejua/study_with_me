package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.reply.ReplyRepository;
import com.ksk.project.study_with_me.web.dto.reply.ReplyListResponseDto;
import com.ksk.project.study_with_me.web.dto.reply.ReplySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    @Transactional
    public Long save(ReplySaveRequestDto requestDto) {
        return replyRepository.save(requestDto.toEntity()).getReplyNo();
    }

    @Transactional(readOnly = true)
    public List<ReplyListResponseDto> findAllByPostNoAndBoardName(Long postNo, String boardName) {
        return replyRepository.findByPostNoAndBoardNameOrderByReplyNoAsc(postNo, boardName).stream()
                .map(ReplyListResponseDto::new)
                .collect(Collectors.toList());
    }
}
