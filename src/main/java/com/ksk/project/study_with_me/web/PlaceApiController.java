package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.service.PlaceService;
import com.ksk.project.study_with_me.util.ImageUtils;
import com.ksk.project.study_with_me.web.dto.place.PlacePostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/board/place")
@RestController
public class PlaceApiController {
    private final PlaceService placeService;

    @PostMapping("/posts")
    public Long save(@RequestBody PlacePostsSaveRequestDto requestDto) {
        return placeService.save(requestDto);
    }

    @PostMapping("/posts/upload/{id}")
    public boolean upload(@RequestParam("thumbnail") MultipartFile thumbnail, @PathVariable Long id) throws IOException {
        String originalFileName = thumbnail.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
        String path = ImageUtils.DEFAULT_PATH + MatchNames.Boards.BOARD_PLACE_RECOMMENDATION.getShortName() + File.separator + id + File.separator;

        File file_path = new File(path);

        if(!file_path.exists()) {
            file_path.mkdirs();
        }

        File dest = new File(path + "thumbnail" + "." + ext);
        thumbnail.transferTo(dest);

        return true;
    }
}
