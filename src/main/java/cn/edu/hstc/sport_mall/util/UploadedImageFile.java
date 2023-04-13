package cn.edu.hstc.sport_mall.util;

import org.springframework.web.multipart.MultipartFile;

/*
 * 工具类-文件上传
 */
public class UploadedImageFile {
    MultipartFile image;
 
    public MultipartFile getImage() {
        return image;
    }
 
    public void setImage(MultipartFile image) {
        this.image = image;
    }
 
}