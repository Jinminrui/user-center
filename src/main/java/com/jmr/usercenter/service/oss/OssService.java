package com.jmr.usercenter.service.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.jmr.usercenter.utils.FileOperator;
import lombok.RequiredArgsConstructor;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OssService {
    private static String accessKeyId = "LTAI4Fsk3ufKP5w3LN61QHhp";
    private static String accessKeySecret = "zAGppLb9O9FWCaGCYi2Bl76sGGZw1T";
    private static String endpoint = "oss-cn-shanghai.aliyuncs.com";

    public String uploadImage(MultipartFile file) throws IOException {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        FileOperator fileOperator = new FileOperator();
        String filename = file.getOriginalFilename();
        String suffix=fileOperator.getFileSuffix(file); // 文件扩展名
        return null;
    }
}
