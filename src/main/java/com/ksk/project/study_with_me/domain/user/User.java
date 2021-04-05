package com.ksk.project.study_with_me.domain.user;

import com.ksk.project.study_with_me.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    //TODO 프로필 기능 추가 여부 : 필드 추가
    @Column(length = 50, nullable = false)
    private String nickname;

/*  @Column
    private String picture;*/

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

    //TODO 프로필 기능 추가 여부 : Update() 수정
    public User changeName(String name) {
        this.name = name;

        return this;
    }

    /**
     * 회원 가입
     */
    public User becomeUser(Role role) {
        this.role = role;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}