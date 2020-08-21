package com.ksk.project.study_with_me.domain.like;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@NoArgsConstructor
public class IsLikePK implements Serializable {
//    private static final long serialVersionUID = 1L;

    @Id
    private Long userCode;

    @Id
    private Long postNo;
}
