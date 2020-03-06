package com.jmr.usercenter.controller.oss;

import com.jmr.usercenter.domain.dto.CommonResponseDTO;
import com.jmr.usercenter.exceptions.OssIOException;
import com.jmr.usercenter.service.oss.OssService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/oss")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OssController {
    private final OssService ossService;

    @PostMapping("/uploadAvatar")
    public CommonResponseDTO<String> uploadAvatar(@RequestParam("file") MultipartFile file, @RequestParam("userId") String id) throws IOException {
        String fileUrl = ossService.uploadAvatar(file, id);
        return CommonResponseDTO.<String>builder().code(200).data(fileUrl).desc("上传成功").build();
    }
}
