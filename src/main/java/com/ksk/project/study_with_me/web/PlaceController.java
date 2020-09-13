package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/board/place")
@Controller
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/posts/list")
    public String findPosts(@PageableDefault(size = 10, sort="createdDate", direction = Sort.Direction.DESC) Pageable pageRequest,
                            Model model) {
        model.addAttribute("list", placeService.findPosts(pageRequest));

        return "board/place/posts-list";
    }

    @GetMapping("/posts/write")
    public String write() {
        return "board/place/posts-save";
    }
}
