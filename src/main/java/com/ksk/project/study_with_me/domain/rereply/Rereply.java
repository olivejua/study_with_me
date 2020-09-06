package com.ksk.project.study_with_me.domain.rereply;

import com.ksk.project.study_with_me.domain.BaseTimeEntity;
import com.ksk.project.study_with_me.domain.reply.Reply;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Rereply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rereplyNo;

    @ManyToOne
    @JoinColumn(name = "replyNo")
    private Reply reply;

    @ManyToOne
    @JoinColumn(name = "userCode")
    private User user;

    @Column
    private String content;

    @Column
    private Long postNo;

    @Column
    private String boardName;

    @Builder
    public Rereply(Reply reply, User user, String content, Long postNo, String boardName) {
        this.reply = reply;
        this.user = user;
        this.content = content;
        this.postNo = postNo;
        this.boardName = boardName;
    }
}
