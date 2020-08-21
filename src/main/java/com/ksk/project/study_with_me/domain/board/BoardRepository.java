package com.ksk.project.study_with_me.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, String> {
}
