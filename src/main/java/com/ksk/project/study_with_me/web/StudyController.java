package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.config.EnumModel;
import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.config.auth.LoginUser;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.service.StudyService;
import com.ksk.project.study_with_me.web.dto.EnumValue;
import com.ksk.project.study_with_me.web.dto.study.StudyPostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        model.addAttribute("conditionLanguages", toEnumValues(MatchNames.ConditionLanguages.class));

        return "/board/study/posts-save";
    }

    @PostMapping("/posts/save")
    public String save(Model model, StudyPostsSaveRequestDto requestDto) {
        return "/board/study/posts-list";
    }

    /*@GetMapping("/posts/read")
    public String read(Model model, Long postNo) {

        return "/board/study/posts-read";
    }*/

    private List<EnumValue> toEnumValues(Class<? extends EnumModel> e) {
        return Arrays
                .stream(e.getEnumConstants())
                .map(EnumValue::new)
                .collect(Collectors.toList());
    }
}
