package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
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
    public String login(Model model) {
        model.addAttribute("login", "login");

        return "index";
    }

    @GetMapping("/processLogin")
    public String processLogin(Model model, HttpSession httpSession) {

        if(httpSession.getAttribute("guest") != null) {
            model.addAttribute("messageToGuest", "손님이시군요. 회원이 되시겠습니까?");

            model.addAttribute("existedNicknameList", userService.findAllNickname());
            model.addAttribute("guest", httpSession.getAttribute("guest"));

            return "index";
        }

        return "redirect:/";
    }

    @PostMapping("/signup")
    public String signup(UserSignupRequestDto requestDto, HttpSession httpSession) {
        UserResponseDto responseDto = userService.save(requestDto);
        httpSession.setAttribute("user", new SessionUser(responseDto.toEntity()));
        return "redirect:/";
    }
}
