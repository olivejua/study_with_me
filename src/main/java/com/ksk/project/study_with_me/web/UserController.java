package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.config.auth.LoginUser;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.domain.user.Role;
import com.ksk.project.study_with_me.service.UserService;
import com.ksk.project.study_with_me.web.dto.user.UserResponseDto;
import com.ksk.project.study_with_me.web.dto.user.UserSignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public void login() {}

    @GetMapping("/processLogin")
    public String processLogin(Model model, @LoginUser SessionUser user) {

        if(user.getRole().equals(Role.GUEST.getKey())) {
            model.addAttribute("messageToGuest", "손님이시군요. 회원이 되시겠습니까?");
            return "index";
        }

        return "redirect:/";
    }

    @GetMapping("/signup")
    public void signup(Model model, @LoginUser SessionUser user, HttpSession httpSession) {
        model.addAttribute("existedNicknameList", userService.findAllNickname());
        model.addAttribute("user", user);

        httpSession.removeAttribute("user");
    }

    @PostMapping("/signup")
    public String signup(UserSignupRequestDto requestDto, HttpSession httpSession) {
        UserResponseDto responseDto = userService.save(requestDto);
        httpSession.setAttribute("user", new SessionUser(responseDto.toEntity()));
        return "redirect:/";
    }
}
