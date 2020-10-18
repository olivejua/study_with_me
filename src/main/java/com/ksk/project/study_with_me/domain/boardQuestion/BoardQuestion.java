package com.ksk.project.study_with_me.domain.boardQuestion;

import com.ksk.project.study_with_me.domain.BaseTimeEntity;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class BoardQuestion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNo;

    @ManyToOne
    @JoinColumn(name = "userCode")
    private User user;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int commentCount;

    @Builder
    public BoardQuestion(User user, String title, String content,
                         int viewCount, int commentCount) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
    }

    public BoardQuestion updateCommentCount(int commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public void addViewCount() {
        this.viewCount += 1;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
