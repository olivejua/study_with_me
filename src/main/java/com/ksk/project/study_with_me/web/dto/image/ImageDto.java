package com.ksk.project.study_with_me.web.dto.image;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageDto {
    //photo_uploader.html페이지의 form태그내에 존재하는 file 태그의 name명과 일치시켜줌
    private MultipartFile fileData;
    //callback URL
    private String callback;
    //콜백함수??
    private String callback_func;

    public ImageDto(MultipartFile fileData, String callback, String callback_func) {
        this.fileData = fileData;
        this.callback = callback;
        this.callback_func = callback_func;
    }
}