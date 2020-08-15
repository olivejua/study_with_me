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

    // TODO: 2020-08-12 양방향할건지 테이블을 어떻게 할건지 고민해보기
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
