package com.ksk.project.study_with_me.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findByPostNoAndBoardNameOrderByCommentNoAsc(Long postNo, String boardName);

    public int countByPostNoAndBoardName(Long postNo, String boardName);
}
