package com.ksk.project.study_with_me.web.dto.websocket;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlarmDto {
    private String content;

    public AlarmDto(String content) {
        this.content = content;
    }
}
