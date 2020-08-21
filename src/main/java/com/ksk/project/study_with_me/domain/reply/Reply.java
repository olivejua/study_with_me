package com.ksk.project.study_with_me.domain.reply;

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
    private String boardName;

    @Column(nullable = false)
    private Long postNo;

    @Column(nullable = false)
    private Long userCode;

    @Column(nullable = false)
    private String content;

    // TODO: Image 와 board 의 Entity관계 설정 : 필드 타입 변경
    private Long imageCode;

    @Builder
    public Reply(String boardName, Long postNo, Long userCode, String content, Long imageCode) {
        this.boardName = boardName;
        this.postNo = postNo;
        this.userCode = userCode;
        this.content = content;
        this.imageCode = imageCode;
    }
}
