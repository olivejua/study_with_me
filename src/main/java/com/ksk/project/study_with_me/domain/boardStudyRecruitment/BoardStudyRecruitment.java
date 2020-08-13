package com.ksk.project.study_with_me.domain.boardStudyRecruitment;

import com.ksk.project.study_with_me.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@IdClass(BoardStudyRecruitmentId.class)
@Entity
public class BoardStudyRecruitment {
    /** Board는 식별관계, User는 비식별관계 */

    @Id
    private String boardCode;

    @Id
    private Long postNo;

    @ManyToOne
    @JoinColumn(name = "boardCode", updatable = false, insertable = false)
    private Board board;

    @Column(nullable = false)
    private Long userCode;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(nullable = false)
    private String conditionLanguages;

    @Column(nullable = false)
    private String conditionRegion;

    @Column(nullable = false)
    private Date conditionStartDate;

    @Column(nullable = false)
    private Date conditionEndDate;

    @Column(nullable = false)
    private int conditionCapacity;

    @Column(nullable = false)
    private String conditionExplanation;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int replyCount;


    @Builder
    public BoardStudyRecruitment(String boardCode, Long userCode, String title, String conditionLanguages,
                                 String conditionRegion, Date conditionStartDate, Date conditionEndDate, int conditionCapacity,
                                 String conditionExplanation, int viewCount, int replyCount) {
        this.boardCode = boardCode;
        this.userCode = userCode;
        this.title = title;
        this.conditionLanguages = conditionLanguages;
        this.conditionRegion = conditionRegion;
        this.conditionStartDate = conditionStartDate;
        this.conditionEndDate = conditionEndDate;
        this.conditionCapacity = conditionCapacity;
        this.conditionExplanation = conditionExplanation;
        this.viewCount = viewCount;
        this.replyCount = replyCount;
    }
}
