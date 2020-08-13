package com.ksk.project.study_with_me.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCode;

    @Column(length = 50, nullable = false)
    private String userName;

    @Column(length = 50, nullable = false)
    private String userNickname;

    @Builder
    public User(String userName, String userNickname) {
        this.userName = userName;
        this.userNickname = userNickname;
    }
}
