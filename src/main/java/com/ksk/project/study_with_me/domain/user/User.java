package com.ksk.project.study_with_me.domain.user;

import com.ksk.project.study_with_me.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCode;

    @Column(nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(length = 50, nullable = false)
    private String nickname;

    private String socialCode;

    @Builder
    public User(Long userCode, String email, String name, Role role, String nickname, String socialCode) {
        this.userCode = userCode;
        this.email = email;
        this.name = name;
        this.role = role;
        this.nickname = nickname;
        this.socialCode = socialCode;
    }

    public User update(String name) {
        this.name = name;

        return this;
    }

    public User changeNickname(String nickname) {
        this.nickname = nickname;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}