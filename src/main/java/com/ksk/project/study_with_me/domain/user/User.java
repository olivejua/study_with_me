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

    //TODO 프로필 기능 추가 여부 : 필드 추가
    /*@Column(length = 50, nullable = false)
    private String userNickname;

    @Column
    private String picture;*/

    @Builder
    public User(String email, String name, Role role) {
        this.email = email;
        this.name = name;
        this.role = role;
    }

    //TODO 프로필 기능 추가 여부 : Update() 수정
    public User update(String name) {
        this.name = name;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
