package com.ksk.project.study_with_me.domain.boardQuestion;

import com.ksk.project.study_with_me.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class BoardQuestion {

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

    @Column(length = 2000, nullable = false)
    private String content;

    // TODO: Image 와 board 의 Entity관계 설정 : 필드 타입 변경
    @Column
    private Long imageCodes;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int replyCount;

    @Builder
    public BoardQuestion(Board board, Long userCode, String title, String content,
                         Long imageCodes, int viewCount, int replyCount) {
        this.board = board;
        this.userCode = userCode;
        this.title = title;
        this.content = content;
        this.imageCodes = imageCodes;
        this.viewCount = viewCount;
        this.replyCount = replyCount;
    }
}
