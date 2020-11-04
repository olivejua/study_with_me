package com.ksk.project.study_with_me.web;

import com.google.gson.Gson;
import com.ksk.project.study_with_me.config.auth.LoginUser;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.service.UserService;
import com.ksk.project.study_with_me.web.dto.user.UserResponseDto;
import com.ksk.project.study_with_me.web.dto.user.UserSignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");

        mav.addObject("login", "login");

        return mav;
    }

    @GetMapping("/processLogin")
    public ModelAndView processLogin(@LoginUser SessionUser user, HttpSession httpSession) {
        ModelAndView mav = new ModelAndView();

        if(user.getRole().equals(Role.GUEST.getKey())) {
            mav.addObject("messageToGuest", "손님이시군요. 회원이 되시겠습니까?");

            mav.addObject("existedNicknameList", userService.findAllNickname());
            mav.addObject("guest", user);
            httpSession.removeAttribute("user");

            mav.setViewName("index");
        } else {
            mav.setViewName("redirect:/");
        }

        return mav;
    }

    @PostMapping("/signup")
    public Long signup(@RequestBody UserSignupRequestDto requestDto, HttpSession httpSession) {
        UserResponseDto responseDto = userService.save(requestDto);
        httpSession.setAttribute("user", new SessionUser(responseDto.toEntity()));

        return responseDto.getUserCode();
    }

    @GetMapping("/changeNickname")
    public String changeNickname() {
        Gson gson = new Gson();

        return gson.toJson(userService.findAllNickname());
    }

    @PutMapping("/changeNickname/{nickname}")
    public Long changeNickname(@LoginUser SessionUser user, @PathVariable String nickname, HttpSession httpSession) {
        UserResponseDto dto = userService.changeNickname(user.getUserCode(), nickname);
        httpSession.setAttribute("user", new SessionUser(dto.toEntity()));

        return user.getUserCode();
    }
}
