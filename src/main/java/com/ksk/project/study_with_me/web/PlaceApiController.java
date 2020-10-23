package com.ksk.project.study_with_me.web;

import com.google.gson.Gson;
import com.ksk.project.study_with_me.config.MatchNames;
import com.ksk.project.study_with_me.config.auth.LoginUser;
import com.ksk.project.study_with_me.config.auth.dto.SessionUser;
import com.ksk.project.study_with_me.service.LikeService;
import com.ksk.project.study_with_me.service.PlaceService;
import com.ksk.project.study_with_me.web.dto.PageDto;
import com.ksk.project.study_with_me.web.dto.place.PostsListResponseDto;
import com.ksk.project.study_with_me.web.dto.place.PostsReadResponseDto;
import com.ksk.project.study_with_me.web.dto.place.PostsSaveRequestDto;
import com.ksk.project.study_with_me.web.dto.place.PostsUpdateRequestDto;
import com.ksk.project.study_with_me.web.dto.study.SearchDto;
import com.ksk.project.study_with_me.web.file.TransferFiles;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/board/place")
@RestController
public class PlaceApiController {
    private final PlaceService placeService;
    private final LikeService likeService;

    @PostMapping("/posts")
    public Long save(PostsSaveRequestDto requestDto, @RequestParam MultipartFile thumbnailFile) {
        Long savedPostNo = placeService.save(requestDto);
        String boardName = MatchNames.Boards.BOARD_PLACE_RECOMMENDATION.getShortName();
        String savedThumbnailName = requestDto.getThumbnailPath().substring(requestDto.getThumbnailPath().lastIndexOf("/")+1);

        TransferFiles.saveImagesByHtmlCode(requestDto.getContent(), boardName, savedPostNo);
        TransferFiles.saveThumbnail(thumbnailFile, boardName, savedThumbnailName);

        return savedPostNo;
    }

    @PutMapping("/posts/{postNo}")
    public Long update(@PathVariable Long postNo, PostsUpdateRequestDto requestDto, @RequestParam(required = false) MultipartFile thumbnailFile) {
        TransferFiles.updateImagesByHtmlCode(requestDto.getContent()
                , MatchNames.Boards.BOARD_PLACE_RECOMMENDATION.getShortName(), postNo);

        if(thumbnailFile != null) {
            TransferFiles.updateThumbnail(thumbnailFile, MatchNames.Boards.BOARD_PLACE_RECOMMENDATION.getShortName()
                    , requestDto.getThumbnailPath().substring(requestDto.getThumbnailPath().lastIndexOf("/")+1)
                    , requestDto.getOldThumbnailPath().substring(requestDto.getOldThumbnailPath().lastIndexOf("/")+1));
        }

        return placeService.update(postNo, requestDto);
    }

    @DeleteMapping("/posts/{postNo}")
    public Long delete(@PathVariable Long postNo) {
        String thumbnailPath = placeService.delete(postNo);

        TransferFiles.deleteAllImagesInDirectory(MatchNames.Boards.BOARD_PLACE_RECOMMENDATION.getShortName(), postNo);
        TransferFiles.deleteThumbnail(MatchNames.Boards.BOARD_PLACE_RECOMMENDATION.getShortName()
                , thumbnailPath.substring(thumbnailPath.lastIndexOf("/")+1));

        return postNo;
    }

    @GetMapping("/list")
    public ModelAndView findPosts(@PageableDefault(size = 10, sort="createdDate", direction = Sort.Direction.DESC) Pageable pageRequest
            , SearchDto searchDto, HttpServletRequest request, @LoginUser SessionUser user) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/place/posts-list");

        Page<PostsListResponseDto> list = searchDto.existSearch() ?
                placeService.searchPosts(pageRequest, searchDto) : placeService.findPosts(pageRequest);

        mav.addObject("list", list);
        mav.addObject("search", searchDto.existSearch() ? searchDto : null);
        TransferFiles.listThumbnails(request.getSession().getServletContext().getRealPath("/")
                , MatchNames.Boards.BOARD_PLACE_RECOMMENDATION.getShortName(), list.getContent());
        mav.addObject("user", user);

        return mav;
    }

    @GetMapping("/list/index")
    public String findPosts_index(@PageableDefault(size = 4, sort="createdDate", direction = Sort.Direction.DESC) Pageable pageRequest
            , HttpServletRequest request) {
        Gson gson = new Gson();

        List<PostsListResponseDto> list = placeService.findPosts(pageRequest).getContent();
        TransferFiles.listThumbnails(request.getSession().getServletContext().getRealPath("/")
                , MatchNames.Boards.BOARD_PLACE_RECOMMENDATION.getShortName(), list);

        return  gson.toJson(list);
    }

    @GetMapping("/posts/write")
    public ModelAndView write(@LoginUser SessionUser user) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/place/posts-save");

        mav.addObject("user", user);

        return mav;
    }

    @GetMapping("/posts/update")
    public ModelAndView update(Long postNo, @LoginUser SessionUser user) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/place/posts-update");

        mav.addObject("post", placeService.findById(postNo));
        mav.addObject("user", user);

        return mav;
    }

    @GetMapping("/posts")
    public ModelAndView read(Long postNo, @LoginUser SessionUser user, HttpServletRequest request, PageDto pageDto) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("board/place/posts-read");

        PostsReadResponseDto responseDto = likeService.findByUserAndPost(placeService.findById(postNo), user);

        mav.addObject("post", responseDto.savePageInfo(pageDto));
        mav.addObject("user", user);

        TransferFiles.readImagesByHtmlCode(responseDto.getContent(), request.getSession().getServletContext().getRealPath("/")
                , MatchNames.Boards.BOARD_PLACE_RECOMMENDATION.getShortName(), postNo
                , responseDto.getThumbnailPath().substring(responseDto.getThumbnailPath().lastIndexOf("/")+1));

        return mav;
    }
}
