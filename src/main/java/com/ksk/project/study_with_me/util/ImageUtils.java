package com.ksk.project.study_with_me.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ImageUtils {
    public static String TEMP_PATH = "";

    public static final String DEFAULT_PATH = "\\home\\ec2-user\\app\\step1\\upload\\";

    public static void saveImages(List<String> imageNames, String categoryName, Long id) {

        if(TEMP_PATH.isEmpty()) {
            System.out.println("=========FileUtils : 'TEMP_PATH'가 비어있습니다.");
            return;
        }

        for(int i=0; i<imageNames.size(); i++) {
            String imageName = imageNames.get(i);

            String savedPath = DEFAULT_PATH + categoryName + File.separator + id + File.separator;
            File file = new File(savedPath);
            System.out.println("=========FileUtils : save path: " + savedPath);

            if(!file.exists()) {
                file.mkdirs();
            }

            if(copy(TEMP_PATH+imageName, savedPath+imageName)) {
                deleteFile(TEMP_PATH+imageName);
            }

        }
    }

    public static boolean copyAllImagesInDir(String sourceDir, String targetDir) {
        File source = new File(sourceDir);
        File target = new File(targetDir);

        try {
            org.apache.commons.io.FileUtils.copyDirectory(source, target);
            return true;
        } catch (IOException e) {
            System.err.println("======ImageUtils.copyAllImagesInDir() : " + source + "가 존재하지 않습니다.");
            return false;
        }
    }

    public static boolean copy(String inFileName, String outFileName) {
        Path source = Paths.get(inFileName);
        Path target = Paths.get(outFileName);

        //사전 체크
        if(source == null) {
            throw new IllegalArgumentException("=========FileUtils : source must be specified");
        }

        if(target == null) {
            throw new IllegalArgumentException("=========FileUtils : target must be specified");
        }


        //소스파일이 실제로 존재하는지 체크
        if(!Files.exists(source, new LinkOption[]{})) {
            throw new IllegalArgumentException("=========FileUtils : Source file doesn't exist: " + source.toString());
        }

        //소스경로와 복사 후 경로가 일치하면 리턴 (일단 주석처리)
        if(source == target) {
            return false;
        }

        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING); //파일 복사
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if(Files.exists(target, new LinkOption[]{})) { //파일이 정상적으로 생성이 되었다면
            System.out.println("=========FileUtils : File Copied");
            return true;
        } else {
            System.out.println("=========FileUtils : File Copy Failed");
            return false;
        }
    }

    public static boolean deleteDirectory(File path) {
        if(!path.exists()) {
            return false;
        }

        File[] files = path.listFiles();
        for(File file : files) {
            if(file.isDirectory()) {
                deleteDirectory(file);
            } else {
                file.delete();
            }
        }

        return path.delete();
    }

    public static void deleteFile(String filePath) {
        if (null != filePath) {
            File delFile = new File(filePath);

            if(delFile.exists()) {
                System.out.println("=========FileUtils : " + delFile.getName() + "파일을 삭제합니다.");
                delFile.delete();
            } else {
                System.out.println("=========FileUtils : " + delFile.getName() + " 파일이 존재하지 않습니다.");
            }
        }
    }

    public static boolean existImages(String html_code) {
        Document doc = Jsoup.parse(html_code);
        Elements images = doc.getElementsByTag("img");

        return images.size() > 0;
    }

    public static List<String> findImageInHTMLCode(String html_code) {
        List<String> imgPathList = new ArrayList<>();

        Document doc = Jsoup.parse(html_code);
        Elements imgs = doc.getElementsByTag("img");
        if(imgs.size() > 0) {
            for(int i=0; i<imgs.size(); i++) {
                String src = imgs.get(i).attr("src");

                String imageName = src.replace("/resource/photo_upload/", "");
                System.out.println("=========RequestDto findImage() : imageName : " + imageName);

                imgPathList.add(imageName);
            }
        }
        return imgPathList;
    }
}
