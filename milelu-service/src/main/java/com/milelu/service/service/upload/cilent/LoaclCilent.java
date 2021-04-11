package com.milelu.service.service.upload.cilent;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.milelu.common.config.MileluConfig;
import com.milelu.common.constant.Constants;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.utils.CommonUtils;
import com.milelu.common.utils.Md5;
import com.milelu.common.utils.file.FileUploadUtils;
import com.milelu.common.utils.file.FileUtils;
import com.milelu.common.utils.uuid.SnowflakeIdWorker;
import com.milelu.system.domain.SysResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MILELU
 * @date 2021/1/13 14:21
 */
@Component
public class LoaclCilent extends UploadClient {

    @Value("${server.tomcat.basedir}")
    private String tempPath;

    private Map<String, String> contentMap = new HashMap<>(16);
    /**
     * 上传文件前触发
     *
     * @param file
     * @return
     */
    @Override
    public AjaxResult readyUpload(MultipartFile file) {
        if (CommonUtils.BeNotNull(file)) {
            return  AjaxResult.success();
        }
        return AjaxResult.error("文件上传失败");
    }

    @Override
    public AjaxResult uploadFiles(MultipartFile[] multipartFiles) {
        return null;
    }

    /**
     * 文件上传中触发
     *
     * @param file
     * @return
     */
    @Override
    public AjaxResult uploadFile(MultipartFile file) {
        try {
            String path = getUploadPath();
            String fileName = FileUploadUtils.upload(path, file);
            Map<String,Object> uploadRes = new HashMap<>(16);
            uploadRes.put("filePath",fileName);
            uploadRes.put("fileFullPath", MileluConfig.getUploadPath()+ File.separator+fileName.replace("profile/upload",""));
            uploadRes.put("url", getUrl()+fileName);
            uploadRes.put("group",getGroup(file));
            uploadRes.put("path",fileName);
            uploadRes.put("fileName",file.getOriginalFilename());
//            uploadRes.put("fileMd5", Md5.md5(file.getInputStream()));
            uploadRes.put("finshed",true);
            return AjaxResult.success(uploadRes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
   public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    factory.setLocation(tempPath);
    return factory.createMultipartConfig();
    }
    @Override
    public void uploadError(AjaxResult result) {

    }

    @Override
    public AjaxResult uploadSuccess(AjaxResult result, MultipartFile file) {
    return saveFileToDb(result,file);
    }




    private String getContentMap(String contentType) {
        if (contentMap.isEmpty()) {
            contentMap.put("text/html", "html");
            contentMap.put("ext/plain", "text");
            contentMap.put("text/xml", "xml");
            contentMap.put("image/gif", "image");
            contentMap.put("image/jpeg", "image");
            contentMap.put("image/png", "image");
            contentMap.put("application/xhtml+xml", "xml");
            contentMap.put("application/pdf", "document");
            contentMap.put("application/msword", "document");
            contentMap.put("application/octet-stream", "file");
        }
        String type = contentMap.get(contentType);
        return CommonUtils.BeNotBlank(type) ? type : "file";
    }

    private String getGroup(MultipartFile multipartFile) {
        return getContentMap(multipartFile.getContentType());
    }

    /**
     * 获取上传路径
     *
     * @return
     */
    private String getUploadPath() {
        return MileluConfig.getUploadPath();
    }

    private String getFilePath(MultipartFile multipartFile) {
        String filePath = getGroup(multipartFile) + Constants.SEPARATOR +
                DateUtil.format(new Date(), "yyyyMMdd") + Constants.SEPARATOR;
        return filePath;
    }

    /**
     * 获取文件名
     */
    private String getFileName(MultipartFile multipartFile) {
        return getFilePath(multipartFile) + SnowflakeIdWorker.getId() + Constants.DOT +
                FileUtil.getSuffix(multipartFile.getOriginalFilename());
    }

    /**
     * 删除文件
     * @param id
     */
    @Override
    public AjaxResult deleteFile(String id) {
        SysResource sysResource = resourceService.selectSysResourceById(id);
        if (CommonUtils.BeNotNull(sysResource)){
            String fileFullPath = sysResource.getFileFullPath();
            boolean deleteFile = FileUtils.deleteFile(fileFullPath);
            if (deleteFile&&resourceService.deleteSysResourceById(id)>0){
               return AjaxResult.success();
            }
            return AjaxResult.error();
        }else {
            return AjaxResult.error("参数不合法");
        }
    }
}
