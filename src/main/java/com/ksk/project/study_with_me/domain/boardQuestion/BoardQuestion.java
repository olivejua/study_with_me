package com.ksk.project.study_with_me.domain.boardQuestion;

import com.ksk.project.study_with_me.config.MatchNames;
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

    @Column(nullable = false)
    private String boardName;

    @Column(nullable = false)
    private Long userCode;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // TODO: Image 와 board 의 Entity관계 설정 : 필드 타입 변경
    @Column
    private Long imageCodes;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int replyCount;

    @Builder
    public BoardQuestion(Long userCode, String title, String content,
                         Long imageCodes, int viewCount, int replyCount) {
        this.boardName = MatchNames.Boards.BOARD_QUESTION.getCalledName();
        this.userCode = userCode;
        this.title = title;
        this.content = content;
        this.imageCodes = imageCodes;
        this.viewCount = viewCount;
        this.replyCount = replyCount;
    }
}
