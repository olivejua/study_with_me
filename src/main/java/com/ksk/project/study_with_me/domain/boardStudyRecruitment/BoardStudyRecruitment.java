package com.ksk.project.study_with_me.domain.boardStudyRecruitment;

import com.ksk.project.study_with_me.domain.BaseTimeEntity;
import com.ksk.project.study_with_me.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class BoardStudyRecruitment extends BaseTimeEntity {
    /** Board, User 비식별관계 */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNo;

    @ManyToOne
    @JoinColumn(name = "userCode")
    private User user;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(nullable = false)
    private String conditionLanguages;

    @Column(nullable = false)
    private String conditionPlace;

    @Column(nullable = false)
    private Date conditionStartDate;

    @Column(nullable = false)
    private Date conditionEndDate;

    @Column(nullable = false)
    private int conditionCapacity;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String conditionExplanation;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int replyCount;

    @Builder
    public BoardStudyRecruitment(User user, String title, String conditionLanguages,
                                 String conditionPlace, Date conditionStartDate, Date conditionEndDate, int conditionCapacity,
                                 String conditionExplanation, int viewCount, int replyCount) {
        this.user = user;
        this.title = title;
        this.conditionLanguages = conditionLanguages;
        this.conditionPlace = conditionPlace;
        this.conditionStartDate = conditionStartDate;
        this.conditionEndDate = conditionEndDate;
        this.conditionCapacity = conditionCapacity;
        this.conditionExplanation = conditionExplanation;
        this.viewCount = viewCount;
        this.replyCount = replyCount;
    }

    public void addViewCount() {
        this.viewCount += 1;
    }
}
