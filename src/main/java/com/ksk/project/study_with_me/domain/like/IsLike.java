package com.ksk.project.study_with_me.domain.like;

import com.ksk.project.study_with_me.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@IdClass(IsLikePK.class)
@Entity
public class IsLike {

    @Id
    private Long userCode;

    @Id
    private String boardCode;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isLike;

    @ManyToOne
    @JoinColumn(name = "boardCode", updatable = false, insertable = false)
    private Board board;

    @Builder
    public IsLike(Long userCode, String boardCode, boolean isLike) {
        this.userCode = userCode;
        this.boardCode = boardCode;
        this.isLike = isLike;
    }
}
