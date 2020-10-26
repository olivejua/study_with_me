package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestion;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestionRepository;
import com.ksk.project.study_with_me.domain.user.User;
import com.ksk.project.study_with_me.web.dto.question.PostsListResponseDto;
import com.ksk.project.study_with_me.web.dto.question.PostsReadResponseDto;
import com.ksk.project.study_with_me.web.dto.question.PostsSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.question.PostsUpdateRequestDto;
import com.ksk.project.study_with_me.web.dto.study.SearchDto;
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

            return new PostsListResponseDto(entity);
        });
    }

    @Transactional(readOnly = true)
    public Page<PostsListResponseDto> searchPosts(Pageable pageable, SearchDto searchDto) {
        String keyword = searchDto.getKeyword();
        Page<BoardQuestion> results = null;
        switch (searchDto.getSearchType()) {
            case "title" :
                results = questionRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable);
                break;
            case "writer" :
                User user = User.builder().nickname(keyword).build();
                results = questionRepository.findByUserContaining(user, pageable);
                break;
            default:
                results = questionRepository.findAll(pageable);
                break;
        }

        return results.map(PostsListResponseDto::new);
    }

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return questionRepository.save(requestDto.toEntity()).getPostNo();
    }

    @Transactional()
    public PostsReadResponseDto findById(Long postNo) {
        BoardQuestion entity = questionRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postNo));

        entity.addViewCount();

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
