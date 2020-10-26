package com.ksk.project.study_with_me.domain.boardQuestion;

import com.ksk.project.study_with_me.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardQuestionRepository extends JpaRepository<BoardQuestion, Long> {

    Page<BoardQuestion> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<BoardQuestion> findByUserContaining(User user, Pageable pageable);
}
