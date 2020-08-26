package com.ksk.project.study_with_me.domain.boardStudyRecruitment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardStudyRecruitmentRepository extends JpaRepository<BoardStudyRecruitment, Long> {

    List<BoardStudyRecruitment> findAllByOrderByPostNoDesc();
}
