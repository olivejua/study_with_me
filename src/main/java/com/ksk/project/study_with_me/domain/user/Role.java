package com.ksk.project.study_with_me.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "사용자");

    private final String key;
    private final String title;

    public static Role findRole(String key) {
        return key.equals(USER.key) ? USER : GUEST;
    }
}
