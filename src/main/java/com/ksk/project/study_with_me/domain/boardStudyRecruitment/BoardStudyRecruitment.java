package com.ksk.project.study_with_me.domain.boardStudyRecruitment;

import com.ksk.project.study_with_me.config.MatchNames;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class BoardStudyRecruitment {
    /** Board, User 비식별관계 */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNo;

    @Column(nullable = false)
    private String boardName;

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
    public BoardStudyRecruitment(Long userCode, String title, String conditionLanguages,
                                 String conditionRegion, Date conditionStartDate, Date conditionEndDate, int conditionCapacity,
                                 String conditionExplanation, int viewCount, int replyCount) {
        this.boardName = MatchNames.BOARD_STUDY_RECRUITMENT.getBoardName();
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
