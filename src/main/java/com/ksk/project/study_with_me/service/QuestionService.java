package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestion;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestionRepository;
import com.ksk.project.study_with_me.web.dto.question.PostsListResponseDto;
import com.ksk.project.study_with_me.web.dto.question.PostsReadResponseDto;
import com.ksk.project.study_with_me.web.dto.question.PostsSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.question.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final BoardQuestionRepository questionRepository;
    private final CommentService commentService;
    private final ReplyService replyService;

    @Transactional(readOnly = true)
    public Page<PostsListResponseDto> findPosts(Pageable pageable) {
        Page<BoardQuestion> posts = questionRepository.findAll(pageable);

        String boardName = MatchNames.Boards.BOARD_QUESTION.getShortName();
        return posts.map(entity -> {
            int commentCount = commentService.countByPostNoAndBoardName(entity.getPostNo(), boardName) +
                    replyService.countByPostNoAndBoardName(entity.getPostNo(), boardName);

            entity.updateCommentCount(commentCount);

            return new PostsListResponseDto(
                    entity.getPostNo(), entity.getUser()
                    , entity.getTitle(), entity.getViewCount()
                    , entity.getCommentCount(), entity.getCreatedDate()
            );
        });
    }

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return questionRepository.save(requestDto.toEntity()).getPostNo();
    }

    @Transactional(readOnly = true)
    public PostsReadResponseDto findById(Long postNo) {
        BoardQuestion entity = questionRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postNo));

        return new PostsReadResponseDto(entity);
    }

    @Transactional
    public Long update(Long postNo, PostsUpdateRequestDto requestDto) {
        BoardQuestion entity = questionRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postNo));

        entity.update(requestDto.getTitle(), requestDto.getContent());

        return postNo;
    }

    @Transactional
    public Long delete(Long postNo) {
        BoardQuestion entity = questionRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postNo));

        questionRepository.delete(entity);

        return entity.getPostNo();
    }

}
