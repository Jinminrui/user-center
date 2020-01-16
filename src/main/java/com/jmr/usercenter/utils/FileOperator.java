package com.jmr.usercenter.utils;

import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileOperator {
    public String getFileSuffix(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public String genAvatarFilepath(String fileName, String userId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTime =  sdf.format(new Date());
        return "avatars/" + userId + "/" + currentTime + "." + getFileSuffix(fileName);
    }
}
