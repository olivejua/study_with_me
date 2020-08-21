package com.ksk.project.study_with_me.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MatchNames {

    BOARD_NOTICE_OF_SEMINAR("세미나개최 알림게시판"),
    BOARD_PLACE_RECOMMENDATION("스터디 장소추천 게시판"),
    BOARD_QUESTION("질문게시판"),
    BOARD_STUDY_RECRUITMENT("스터디모집 게시판");

    private final String boardName;

}
