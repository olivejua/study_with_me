package com.ksk.project.study_with_me.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
public class MatchNames {

    @Getter
    @RequiredArgsConstructor
    public enum Boards {
        BOARD_PLACE_RECOMMENDATION("스터디 장소추천 게시판", "place"),
        BOARD_QUESTION("질문게시판", "question"),
        BOARD_STUDY_RECRUITMENT("스터디모집 게시판", "study");

        private final String calledName;
        private final String shortName;
    }

    @Getter
    @RequiredArgsConstructor
    public enum MessageType {
        Message("message"),
        Reply("reply");

        private final String type;
    }
}
