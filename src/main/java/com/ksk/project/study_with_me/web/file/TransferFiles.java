package com.ksk.project.study_with_me.web.file;

import com.ksk.project.study_with_me.util.ImageUtils;
import com.ksk.project.study_with_me.web.dto.place.PostsListResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TransferFiles {

    public static boolean saveImagesByHtmlCode(String htmlCode, String boardName, Long postNo) {
        if(!ImageUtils.existImages(htmlCode)) {
            return false;
        }

        System.out.println("post save() FileUtils.IMAGE_TEMP_PATH : "+ ImageUtils.TEMP_PATH);

        List<String> imageList = ImageUtils.findImageInHTMLCode(htmlCode);
        if (imageList.size() > 0) {
            ImageUtils.saveImages(imageList, boardName, postNo);
        }

        return true;
    }

    public static boolean updateImagesByHtmlCode(String htmlCode, String boardName, Long postNo) {
        ImageUtils.deleteDirectory(
                new File(ImageUtils.DEFAULT_PATH + boardName + File.separator + postNo));

        return saveImagesByHtmlCode(htmlCode, boardName, postNo);
    }

    public static void deleteAllImagesInDirectory(String boardName, Long postNo) {
        ImageUtils.deleteDirectory(
                new File(ImageUtils.DEFAULT_PATH + boardName + File.separator + postNo));
    }

    public static boolean readImagesByHtmlCode(String htmlCode, String defaultPath, String boardName, Long postNo) {
        if(!ImageUtils.existImages(htmlCode)) {
            return false;
        }

        String copy_path = defaultPath + "resource" + File.separator + "photo_upload" + File.separator;
        String sourcePath = ImageUtils.DEFAULT_PATH + boardName + File.separator + postNo;

        ImageUtils.copyAllImagesInDir(sourcePath, copy_path);

        return true;
    }


    //TODO Util하고 기능 나누기
    public static boolean savedThumbnail(MultipartFile thumbnail, String thumbnailPath) {
        try {
            File dest = new File(thumbnailPath);
            thumbnail.transferTo(dest);

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean listThumbnails(String defaultPath, String boardName, List<PostsListResponseDto> posts) {
        if(posts.size() == 0) {
            return false;
        }

        for(PostsListResponseDto post : posts) {
            String[] paths = post.getThumbnailPath().split("/");

            if(paths.length > 3) {
                String copy_path = defaultPath + paths[1] + File.separator + paths[2] + File.separator + paths[3];
                String sourcePath = ImageUtils.DEFAULT_PATH + boardName + File.separator + post.getPostNo() + File.separator + paths[3];

                ImageUtils.copyAllImagesInDir(sourcePath, copy_path);
            }
        }

        return true;
    }
}
