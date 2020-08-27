package com.ksk.project.study_with_me.web.dto;

import com.ksk.project.study_with_me.config.EnumModel;
import lombok.Getter;

@Getter
public class EnumValue {
    private String key;
    private String value;

    public EnumValue(EnumModel enumModel) {
        this.key = enumModel.getKey();
        this.value = enumModel.getValue();
    }
}
