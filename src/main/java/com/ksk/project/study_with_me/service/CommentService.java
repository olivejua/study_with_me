package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.domain.comment.Comment;
import com.ksk.project.study_with_me.domain.comment.CommentRepository;
import com.ksk.project.study_with_me.web.dto.comment.CommentListResponseDto;
import com.ksk.project.study_with_me.web.dto.comment.CommentSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.comment.CommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReplyService replyService;

    @Transactional
    public Long save(CommentSaveRequestDto requestDto) {
        return commentRepository.save(requestDto.toEntity()).getCommentNo();
    }

    @Transactional
    public Long update(Long commentNo, CommentUpdateRequestDto requestDto) {
        Comment entity = commentRepository.findById(commentNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + commentNo));

        entity.update(requestDto.getContent());

        return commentNo;
    }

    @Transactional(readOnly = true)
    public List<CommentListResponseDto> findAllByPostNoAndBoardName(Long postNo, String boardName) {
        return commentRepository.findByPostNoAndBoardNameOrderByCommentNoAsc(postNo, boardName).stream()
                .map(entity -> new CommentListResponseDto(entity, replyService.findAllByCommentNo(entity.getCommentNo())))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public int countByPostNoAndBoardName(Long postNo, String boardName) {
        return commentRepository.countByPostNoAndBoardName(postNo, boardName);
    }

    @Transactional
    public void delete(Long commentNo) {
        Comment entity = commentRepository.findById(commentNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. commentNo=" + commentNo));

        replyService.deleteAllByCommentNo(commentNo);

        commentRepository.delete(entity);
    }
}
