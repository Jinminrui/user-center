package com.jmr.usercenter.service.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.jmr.usercenter.config.AliyunConfig;
import com.jmr.usercenter.utils.FileOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OssService {

    private final AliyunConfig aliyunConfig;

    public String uploadAvatar(MultipartFile file, String id) throws IOException {
        FileOperator fileOperator = new FileOperator();
        String filename = file.getOriginalFilename();
        String filepath = fileOperator.genAvatarFilepath(filename, id);
        log.info("上传的文件路径为：{}", filepath);

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(
                aliyunConfig.getOssEndpoint(),
                aliyunConfig.getAccessKeyId(),
               aliyunConfig.getAccessKeySecret()
        );

        ossClient.putObject(aliyunConfig.getBucketName(), filepath, new ByteArrayInputStream(file.getBytes()));

        return aliyunConfig.getOssUrlPrefix() + filepath;
    }
}
