package com.ksk.project.study_with_me.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
public class MatchNames {

    @Getter
    @RequiredArgsConstructor
    public enum Boards {
        BOARD_NOTICE_OF_SEMINAR("세미나개최 알림게시판"),
        BOARD_PLACE_RECOMMENDATION("스터디 장소추천 게시판"),
        BOARD_QUESTION("질문게시판"),
        BOARD_STUDY_RECRUITMENT("스터디모집 게시판");

        private final String name;
    }

    @Getter
    @RequiredArgsConstructor
    public enum ConditionLanguages implements EnumModel{

        JAVA("JAVA"),
        SPRING_BOOT("Spring boot"),
        JPA("JPA"),
        DATA_STRUCTURE("자료구조"),
        NODEJS("Node.js"),
        JS("Java Script"),
        AWS("Amazon Web Service");

        private final String value;

        @Override
        public String getKey() {
            return name();
        }

        @Override
        public String getValue() {
            return value;
        }
    }
}
