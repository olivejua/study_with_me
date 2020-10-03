package com.ksk.project.study_with_me.web;

import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.config.auth.LoginUser;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.service.LikeService;
import com.ksk.project.study_with_me.service.PlaceService;
import com.ksk.project.study_with_me.util.ImageUtils;
import com.ksk.project.study_with_me.web.dto.place.PostsReadResponseDto;
import com.ksk.project.study_with_me.web.dto.place.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/board/place")
@RestController
public class PlaceApiController {
    private final PlaceService placeService;
    private final LikeService likeService;

    @PostMapping("/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {

        Long savedPostNo = placeService.save(requestDto);

        //이미지 업로드
        if(ImageUtils.existImages(requestDto.getContent())) {
            System.out.println("post save() FileUtils.IMAGE_TEMP_PATH : "+ ImageUtils.TEMP_PATH);

            List<String> imageList = ImageUtils.findImageInHTMLCode(requestDto.getContent());
            if (imageList.size() > 0) {
                ImageUtils.saveImages(imageList, MatchNames.Boards.BOARD_PLACE_RECOMMENDATION.getShortName(), savedPostNo);
            }
        }

        return savedPostNo;
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

    @GetMapping("/posts/list")
    public ModelAndView findPosts(@PageableDefault(size = 10, sort="createdDate", direction = Sort.Direction.DESC) Pageable pageRequest) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/place/posts-list");

        mav.addObject("list", placeService.findPosts(pageRequest));

        return mav;
    }

    @GetMapping("/posts/write")
    public ModelAndView write(@LoginUser SessionUser user) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/place/posts-save");

        mav.addObject("user", user);

        return mav;
    }

    @GetMapping("/posts/read")
    public ModelAndView read(Long postNo, @LoginUser SessionUser user, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/place/posts-read");

        PostsReadResponseDto responseDto = likeService.findByUserAndPost(placeService.findById(postNo));

        mav.addObject("post", responseDto);
        mav.addObject("user", user);

        if(ImageUtils.existImages(responseDto.getContent())) {
            String defaultPath = request.getSession().getServletContext().getRealPath("/");
            String copy_path = defaultPath + "resource" + File.separator + "photo_upload" + File.separator;

            String sourcePath = ImageUtils.DEFAULT_PATH + MatchNames.Boards.BOARD_PLACE_RECOMMENDATION.getShortName() + File.separator + postNo;

            ImageUtils.copyAllImagesInDir(
                    sourcePath, copy_path);
        }

        return mav;
    }
}
