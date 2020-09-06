package com.ksk.project.study_with_me.web.dto.websocket;

import lombok.Getter;

@Getter
public class MessageDto {
//    private MatchNames.MessageType type;
//    private String title;
    private String content;
//    private String sender;
//    private String recipient;

    public MessageDto(/*MatchNames.MessageType type, String title, */String content/*, String sender, String recipient*/) {
//        this.type = type;
//        this.title = title;
        this.content = content;
//        this.sender = sender;
//        this.recipient = recipient;
    }
}
