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

        String sourcePath = ImageUtils.DEFAULT_PATH + boardName + File.separator + postNo;
        String copy_path = defaultPath + "resource" + File.separator + "photo_upload" + File.separator;

        //content 이미지 복사
        ImageUtils.copyAllImagesInDir(sourcePath, copy_path);

        return true;
    }

    public static boolean readImagesByHtmlCode(String htmlCode, String defaultPath, String boardName
            , Long postNo, String thumbnailName) {

        readImagesByHtmlCode(htmlCode, defaultPath, boardName, postNo);

        String source_path = ImageUtils.DEFAULT_PATH + boardName + File.separator + "thumbnail" + File.separator;
        String copy_path = defaultPath + "resource" + File.separator + "photo_upload" + File.separator;

        ImageUtils.makeDirectory(copy_path);

        //thumbnail 이미지 복사
        ImageUtils.copy(source_path+thumbnailName, copy_path+thumbnailName);

        return true;
    }


    //TODO Util하고 기능 나누기
    public static boolean saveThumbnail(MultipartFile thumbnail, String boardName, String thumbnailName) {
        try {
            String thumbnailPath = ImageUtils.DEFAULT_PATH + File.separator + boardName + File.separator + "thumbnail" +  File.separator;
            File file_thumbnailPath = new File(thumbnailPath);

            if(!file_thumbnailPath.exists()) {
                System.out.println("==============TransferFiles.savedThumbnail(): Thumbnail Path doesn't exist. make directory.");
                file_thumbnailPath.mkdirs();
            }

            File dest = new File(thumbnailPath + thumbnailName);
            thumbnail.transferTo(dest);
            System.out.println("==============TransferFiles.savedThumbnail(): Success Transferring thumbnail file.");

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean updateThumbnail(MultipartFile newThumbnailFile, String boardName, String newThumbnailName, String oldThumbnailName) {
        deleteThumbnail(boardName, oldThumbnailName);

        return saveThumbnail(newThumbnailFile, boardName, newThumbnailName);
    }

    public static boolean deleteThumbnail(String boardName, String thumbnailName) {
        String thumbnailPath = ImageUtils.DEFAULT_PATH + boardName + File.separator + "thumbnail" + File.separator + thumbnailName;

        ImageUtils.deleteFile(thumbnailPath);

        return true;
    }

    public static boolean listThumbnails(String defaultPath, String boardName, List<PostsListResponseDto> posts) {
        if(posts.size() == 0) {
            return false;
        }

        String source_path = ImageUtils.DEFAULT_PATH + boardName + File.separator + "thumbnail" + File.separator;
        String copy_path = defaultPath + "resource" + File.separator + "photo_upload" + File.separator;

        ImageUtils.makeDirectory(copy_path);

        for(PostsListResponseDto post : posts) {
            String thumbnailName = post.getThumbnailPath().substring(post.getThumbnailPath().lastIndexOf("/")+1);

            ImageUtils.copy(source_path + thumbnailName, copy_path + thumbnailName);
        }

        return true;
    }
}
