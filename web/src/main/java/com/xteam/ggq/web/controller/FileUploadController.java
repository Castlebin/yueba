package com.xteam.ggq.web.controller;

import com.xteam.ggq.Application;
import com.xteam.ggq.web.controller.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Slf4j
@RestController
public class FileUploadController {

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ApiResponse handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String name = Application.ROOT + "/" + UUID.randomUUID().toString()
                        + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                File target = new File(name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(target));
                FileCopyUtils.copy(file.getInputStream(), stream);
                stream.close();
                return ApiResponse.returnSuccess(name, "文件上传成功！");
            } catch (Exception e) {
                return ApiResponse.returnFail(-1, "文件上传失败！");
            }
        } else {
            return ApiResponse.returnFail(-1, "文件大小为空！");
        }
    }

}
