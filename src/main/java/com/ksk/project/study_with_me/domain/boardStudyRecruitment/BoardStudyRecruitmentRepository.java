package com.ksk.project.study_with_me.domain.boardStudyRecruitment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardStudyRecruitmentRepository extends JpaRepository<BoardStudyRecruitment, Long> {

    Page<BoardStudyRecruitment> findAll(Pageable pageable);

    @Query("SELECT post FROM BoardStudyRecruitment AS post WHERE :searchType LIKE %:keyword%")
    Page<BoardStudyRecruitment> findAllSearch(String searchType, String keyword, Pageable pageable);

    Page<BoardStudyRecruitment> findByTitleContaining(String keyword, Pageable pageable);
    Page<BoardStudyRecruitment> findByConditionLanguagesContaining(String keyword, Pageable pageable);
    Page<BoardStudyRecruitment> findByConditionPlaceContaining(String keyword, Pageable pageable);
    Page<BoardStudyRecruitment> findByConditionCapacityContaining(String keyword, Pageable pageable);
    Page<BoardStudyRecruitment> findByConditionExplanationContaining(String keyword, Pageable pageable);
}
