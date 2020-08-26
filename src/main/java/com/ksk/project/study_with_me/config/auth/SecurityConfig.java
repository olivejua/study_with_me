package com.ksk.project.study_with_me.config.auth;

import com.ksk.project.study_with_me.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    //TODO 권한에 맞춰서 URL 설정하기 (지금 유저만 가능하게 하는 거 안됨)

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/board/study/posts/list", "/user/login", "/user/signup", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    .antMatchers("/board/study/posts/save").hasRole(Role.USER.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/user/processLogin")
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }
}
