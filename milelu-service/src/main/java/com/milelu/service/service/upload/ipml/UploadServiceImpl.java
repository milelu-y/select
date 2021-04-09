package com.milelu.service.service.upload.ipml;

import com.milelu.common.core.domain.AjaxResult;
import com.milelu.service.service.upload.UploadService;
import com.milelu.service.service.upload.cilent.BaseUpload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author MILELU
 * @date 2021/1/13 14:19
 */
@Service
public class UploadServiceImpl extends BaseUpload implements UploadService {

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @Override
    public AjaxResult uploadFile(MultipartFile file) {
        AjaxResult result = uploadClient.readyUpload(file);
        Integer code = (Integer) result.get("code");
        if (code == 200) {
            result = uploadClient.uploadFile(file);
            code = (Integer) result.get("code");
            if (200 == code) {
                result = uploadClient.uploadSuccess(result, file);
            }
        } else {
            uploadClient.uploadError(result);
        }
        return result;
    }

    /**
     * 删除文件
     *
     * @param id
     */
    @Override
    public AjaxResult deleteFile(String id) {
      return   uploadClient.deleteFile(id);
    }
}
