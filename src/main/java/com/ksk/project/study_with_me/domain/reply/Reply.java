package com.ksk.project.study_with_me.domain.reply;

import com.ksk.project.study_with_me.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyNo;

    @Column(nullable = false)
    private String boardCode;

    @Column(nullable = false)
    private Long postNo;

    @Column(nullable = false)
    private Long userCode;

    @Column(nullable = false)
    private String content;

    // TODO: 2020-08-12 양방향할건지 테이블을 어떻게 할건지 고민해보기
    private Long imageCode;

    @Builder
    public Reply(String boardCode, Long postNo, Long userCode, String content, Long imageCode) {
        this.boardCode = boardCode;
        this.postNo = postNo;
        this.userCode = userCode;
        this.content = content;
        this.imageCode = imageCode;
    }
}
