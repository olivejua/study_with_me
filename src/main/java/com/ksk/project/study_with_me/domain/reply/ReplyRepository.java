package com.ksk.project.study_with_me.domain.reply;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    public List<Reply> findByPostNoAndBoardNameOrderByReplyNoAsc(Long postNo, String boardName);
}
