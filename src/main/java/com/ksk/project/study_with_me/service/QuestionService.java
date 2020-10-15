package com.ksk.project.study_with_me.service;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestion;
import com.ksk.project.study_with_me.domain.boardQuestion.BoardQuestionRepository;
import com.ksk.project.study_with_me.web.dto.question.QuestionPostsListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final BoardQuestionRepository questionRepository;
    private final ReplyService replyService;
    private final RereplyService rereplyService;

    @Transactional(readOnly = true)
    public Page<QuestionPostsListResponseDto> findPosts(Pageable pageable) {
        Page<BoardQuestion> posts = questionRepository.findAll(pageable);

        String boardName = MatchNames.Boards.BOARD_QUESTION.getShortName();
        return posts.map(entity -> {
            int replyCount = replyService.countByPostNoAndBoardName(entity.getPostNo(), boardName) +
                    rereplyService.countByPostNoAndBoardName(entity.getPostNo(), boardName);

            entity.updateReplyCount(replyCount);

            return new QuestionPostsListResponseDto(
                    entity.getPostNo(), entity.getUser()
                    , entity.getTitle(), entity.getViewCount()
                    , entity.getReplyCount(), entity.getCreatedDate()
            );
        });
    }

}
