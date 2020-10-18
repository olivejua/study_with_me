package com.ksk.project.study_with_me.web.dto.question;

import lombok.Getter;

@Getter
public class PostsUpdateRequestDto {
    private String title;
    private String content;

    public PostsUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
