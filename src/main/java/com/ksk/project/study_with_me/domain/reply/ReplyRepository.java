package com.ksk.project.study_with_me.domain.reply;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    public List<Reply> findByPostNoAndBoardNameOrderByReplyNo(Long postNo, String boardName);

    public List<Reply> findByCommentNoOrderByReplyNoAsc(Long commentNo);

    public void deleteAllByCommentNo(Long commentNo);

    public int countByPostNoAndBoardName(Long postNo, String boardName);
}
