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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/user/login", "/user/signup"
                            , "/board/study/list/**", "/board/place/list/**", "/board/question/list/**"
                            , "/css/**", "/img/**", "/js/**", "/h2-console/**", "/resource/photo_upload/**").permitAll()
                    .antMatchers("/board/study/posts/**", "/board/place/posts/**", "/board/question/posts/**").hasRole(Role.USER.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .exceptionHandling()
                        .accessDeniedPage("/user/processLogin")
                .and()
                    .oauth2Login()
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/user/processLogin")
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }
}
