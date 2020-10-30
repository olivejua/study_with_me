package com.ksk.project.study_with_me.domain.comment;

import com.ksk.project.study_with_me.domain.BaseTimeEntity;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentNo;

    @Column(nullable = false)
    private String boardName;

    @Column(nullable = false)
    private Long postNo;

    @ManyToOne
    @JoinColumn(name = "userCode")
    private User user;

    @Column(nullable = false)
    private String content;

    //TODO 테스트 검증 끝나면 지우기
//    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
//    private List<Reply> replies;

    @Builder
    public Comment(Long commentNo, String boardName, Long postNo, User user, String content) {
        this.commentNo = commentNo;
        this.boardName = boardName;
        this.postNo = postNo;
        this.user = user;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
