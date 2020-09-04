package com.ksk.project.study_with_me.domain.reply;

import com.ksk.project.study_with_me.domain.BaseTimeEntity;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyNo;

    @Column(nullable = false)
    private String boardName;

    @Column(nullable = false)
    private Long postNo;

    @ManyToOne
    @JoinColumn(name = "userCode")
    private User user;

    @Column(nullable = false)
    private String content;

    // TODO: Image 와 board 의 Entity관계 설정 : 필드 타입 변경
    private Long imageCode;

    @Builder
    public Reply (Long replyNo, String boardName, Long postNo, User user, String content, Long imageCode) {
        this.replyNo = replyNo;
        this.boardName = boardName;
        this.postNo = postNo;
        this.user = user;
        this.content = content;
        this.imageCode = imageCode;
    }
}
