package com.milelu.service.service.upload;

import com.milelu.common.core.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author MILELU
 * @date 2021/1/13 14:19
 */
public interface UploadService {
    /**
     * 上传文件
     * @param file
     * @return
     */
    AjaxResult uploadFile(MultipartFile file);

    /**
     * 删除文件
     * @param id
     */
    AjaxResult deleteFile(String id);
}
