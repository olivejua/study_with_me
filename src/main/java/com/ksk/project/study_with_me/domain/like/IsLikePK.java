package com.ksk.project.study_with_me.domain.like;

import com.ksk.project.study_with_me.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@NoArgsConstructor
public class IsLikePK implements Serializable {
//    private static final long serialVersionUID = 1L;

    @Id
    private Long userCode;

    @Id
    private String boardCode;
}
