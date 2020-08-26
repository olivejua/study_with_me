package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.config.auth.LoginUser;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/board/study")
@Controller
public class StudyController {

    private final StudyService studyService;

    @GetMapping("/posts/list")
    public String list(Model model) {
        model.addAttribute("posts", studyService.findAllDesc());

        return "/board/study/posts-list";
    }

    @GetMapping("/posts/save")
    public String save(Model model, @LoginUser SessionUser user) {
        model.addAttribute("user", user);

        return "/board/study/posts-save";
    }

    /*@GetMapping("/posts/read")
    public String read(Model model, Long postNo) {

        return "/board/study/posts-read";
    }*/
}
