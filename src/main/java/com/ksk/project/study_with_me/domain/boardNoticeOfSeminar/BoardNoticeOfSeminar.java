package com.ksk.project.study_with_me.domain.boardNoticeOfSeminar;

import com.ksk.project.study_with_me.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class BoardNoticeOfSeminar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNo;

    @ManyToOne
    @JoinColumn(name = "boardCode")
    private Board board;

    @Column(nullable = false)
    private Long userCode;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String address;

    // TODO: Image 와 board 의 Entity관계 설정 : 필드 타입 변경
    @Column
    private Long imageCodes;

    @Column(nullable = false)
    private int viewCount;

    @Builder
    public BoardNoticeOfSeminar(Board board, Long userCode, String title, String address,
                                Long imageCodes, int viewCount) {
        this.board = board;
        this.userCode = userCode;
        this.title = title;
        this.address = address;
        this.imageCodes = imageCodes;
        this.viewCount = viewCount;
    }
}
