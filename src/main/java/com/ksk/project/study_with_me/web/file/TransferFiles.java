package com.ksk.project.study_with_me.web.file;

import com.ksk.project.study_with_me.util.ImageUtils;
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

        ImageUtils.copyAllImagesInDir(
                sourcePath, copy_path);

        return true;
    }

    //TODO Util하고 기능 나누기
    public static boolean savedThumbnail(MultipartFile thumbnail, String boardName, Long postNo) {
        try {
            String originalFileName = thumbnail.getOriginalFilename();
            String ext = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
            String path = ImageUtils.DEFAULT_PATH + boardName + File.separator + postNo + File.separator;

            File file_path = new File(path);

            if(!file_path.exists()) {
                file_path.mkdirs();
            }

            File dest = new File(path + "thumbnail" + "." + ext);
            thumbnail.transferTo(dest);

            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
