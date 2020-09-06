package com.ksk.project.study_with_me.domain.rereply;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RereplyRepository extends JpaRepository<Rereply, Long> {
    public List<Rereply> findByPostNoAndBoardNameOrderByReplyAsc(Long postNo, String boardName);
}
