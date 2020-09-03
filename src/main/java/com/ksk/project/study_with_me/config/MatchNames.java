package com.ksk.project.study_with_me.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
public class MatchNames {

    @Getter
    @RequiredArgsConstructor
    public enum Boards {
        BOARD_NOTICE_OF_SEMINAR("세미나개최 알림게시판", "seminar"),
        BOARD_PLACE_RECOMMENDATION("스터디 장소추천 게시판", "place"),
        BOARD_QUESTION("질문게시판", "question"),
        BOARD_STUDY_RECRUITMENT("스터디모집 게시판", "study");

        private final String calledName;
        private final String dbName;
    }
}
