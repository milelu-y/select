package com.milelu.web.controller.upload;

import com.milelu.common.core.domain.AjaxResult;
import com.milelu.service.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author MILELU
 * @date 2021/1/13 14:17
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    UploadService uploadService;

    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file){
        return uploadService.uploadFile(file);
    }

    @DeleteMapping("/deleteFile/{id}")
    public AjaxResult deleteFile(@PathVariable String id){
        return uploadService.deleteFile(id);
    }

}
