package com.ksk.project.study_with_me.web.dto;

import lombok.Getter;

@Getter
public class TestDto {
    private String name;
    private String content;
//    private MultipartFile uploadFile;

    public TestDto(String name, String content/*, MultipartFile uploadFile*/) {
        this.name = name;
        this.content = content;
//        this.uploadFile = uploadFile;
    }
}
