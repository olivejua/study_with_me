package com.ksk.project.study_with_me.domain.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Board {

    @Id
    private String boardCode;

    @Column(length = 50, nullable = false)
    private String boardName;

    @Builder
    public Board(String boardCode, String boardName) {
        this.boardCode = boardCode;
        this.boardName = boardName;
    }
}
