package com.jmr.usercenter.utils;

import org.springframework.web.multipart.MultipartFile;

public class FileOperator {
    public String getFileSuffix(MultipartFile file) {
        String filename = file.getName();
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
