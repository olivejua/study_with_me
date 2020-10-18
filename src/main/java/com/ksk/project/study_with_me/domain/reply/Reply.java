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

    //TODO 테스트 검증 끝나면 지우기
//    @ManyToOne
//    @JoinColumn(name = "commentNo")
//    private Comment comment;

    @Column(nullable = false)
    private Long commentNo;

    @ManyToOne
    @JoinColumn(name = "userCode")
    private User user;

    @Column
    private String content;

    @Column(nullable = false)
    private Long postNo;

    @Column(nullable = false)
    private String boardName;

    @Builder
    public Reply(Long commentNo, User user, String content, Long postNo, String boardName) {
        this.commentNo = commentNo;
        this.user = user;
        this.content = content;
        this.postNo = postNo;
        this.boardName = boardName;
    }

    public void update(String content) {
        this.content = content;
    }
}
