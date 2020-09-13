package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.config.auth.LoginUser;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.service.RereplyService;
import com.ksk.project.study_with_me.web.dto.rereply.RereplySaveRequestDto;
import com.ksk.project.study_with_me.web.dto.rereply.RereplyUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/board")
@RestController
public class RereplyApiController {
    private final RereplyService rereplyService;

    @PostMapping("/posts/rereply")
    public Long save(@RequestBody RereplySaveRequestDto dto, @LoginUser SessionUser user) {
        return rereplyService.save(dto.setUser(user.toEntity()));
    }

    @PutMapping("/posts/rereply/{rereplyNo}")
    public Long update(@PathVariable Long rereplyNo, @RequestBody RereplyUpdateRequestDto dto) {
        return rereplyService.update(rereplyNo, dto);
    }

    @DeleteMapping("/posts/rereply/{rereplyNo}")
    public Long delete(@PathVariable Long rereplyNo) {
        rereplyService.delete(rereplyNo);

        return rereplyNo;
    }
}
