package com.ksk.project.study_with_me.domain.boardPlaceRecommendation;

import com.ksk.project.study_with_me.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class BoardPlaceRecommendation {
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

    // TODO: 2020-08-12 양방향할건지 테이블을 어떻게 할건지 고민해보기
    @Column
    private Long thumbnailImageCode;

    @Column(nullable = false)
    private int likeCount;

    @Column(nullable = false)
    private int dislikeCount;

    @Column(nullable = false)
    private int viewCount;

    @Builder
    public BoardPlaceRecommendation(Board board, Long userCode, String title, String address, Long imageCodes
                                    , Long thumbnailImageCode, int likeCount, int dislikeCount, int viewCount) {
        this.board = board;
        this.userCode = userCode;
        this.title = title;
        this.address = address;
        this.imageCodes = imageCodes;
        this.thumbnailImageCode = thumbnailImageCode;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.viewCount = viewCount;
    }
}
