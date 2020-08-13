package com.ksk.project.study_with_me.domain.boardStudyRecruitment;

import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@NoArgsConstructor
public class BoardStudyRecruitmentId implements Serializable {

    @Id
    private String boardCode;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postNo;
}
