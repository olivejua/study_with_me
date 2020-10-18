package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.reply.Reply;
import com.ksk.project.study_with_me.domain.reply.ReplyRepository;
import com.ksk.project.study_with_me.web.dto.reply.ReplyListResponseDto;
import com.ksk.project.study_with_me.web.dto.reply.ReplySaveRequestDto;
import com.ksk.project.study_with_me.web.dto.reply.ReplyUpdateRequestDto;
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
        return replyRepository.findByPostNoAndBoardNameOrderByReplyNo(postNo, boardName).stream()
                .map(ReplyListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public int countByPostNoAndBoardName(Long postNo, String boardName) {
        return replyRepository.countByPostNoAndBoardName(postNo, boardName);
    }

    @Transactional(readOnly = true)
    public List<ReplyListResponseDto> findAllByCommentNo(Long commentNo) {
        return replyRepository.findByCommentNoOrderByReplyNoAsc(commentNo).stream()
                .map(ReplyListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long replyNo) {
        Reply entity = replyRepository.findById(replyNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 답글이 없습니다. replyNo=" + replyNo));

        replyRepository.delete(entity);
    }

    @Transactional
    public void deleteAllByCommentNo(Long commentNo) {
        replyRepository.deleteAllByCommentNo(commentNo);
    }

    @Transactional
    public Long update(Long replyNo, ReplyUpdateRequestDto requestDto) {
        Reply entity = replyRepository.findById(replyNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 답글이 없습니다. replyNo=" + replyNo));

        entity.update(requestDto.getContent());

        return replyNo;
    }
}
