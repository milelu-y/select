package com.milelu.service.service.upload.cilent;

import cn.hutool.core.io.FileUtil;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.utils.SecurityUtils;
import com.milelu.common.utils.ServletUtils;
import com.milelu.common.utils.uuid.SnowflakeIdWorker;
import com.milelu.system.domain.SysResource;
import com.milelu.system.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author MILELU
 * @date 2021/1/13 14:23
 */
@Component
public abstract class UploadClient {

    private final  String DATA = "data";

    @Autowired
    SysResourceService resourceService;
    /**
     * 文件上传前触发
     *
     * @param file
     * @return
     */
    public abstract AjaxResult readyUpload(MultipartFile file);

    /**
     * 上传多个文件
     * @param multipartFiles
     * @return
     */
    public abstract AjaxResult uploadFiles(MultipartFile[] multipartFiles);
    /**
     * 文件上传中触发
     *
     * @param file
     * @return
     */
    public abstract AjaxResult uploadFile(MultipartFile file);

    /**
     * 文件上传失败触发
     *
     * @param result
     * @return
     */
    public abstract void uploadError(AjaxResult result);

    /**
     * 获取完整的请求路径，包括：域名，端口，上下文访问路径
     *
     * @return 服务地址
     */
    public String getUrl() {
        HttpServletRequest request = ServletUtils.getRequest();
        return getDomain(request);
    }

    public static String getDomain(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }

    public AjaxResult saveFileToDb(AjaxResult resMap, MultipartFile file) {
        Map<String, String> params = (Map<String, String>) resMap.get(DATA);
        String id = SnowflakeIdWorker.getId();
        String uid = UUID.randomUUID().toString();
        SysResource resource = new SysResource();
        String path = params.get("path");
        String group = params.get("group");
//        String fileMd5=params.get("fileMd5");
        resource.setFileName(file.getOriginalFilename()).setFilePath(path).
                setFileFullPath(params.get("fileFullPath")).setGroupName(group).
                setId(id).setFileSize(file.getSize()).setFileUid(uid).setUrl(params.get("url")).
                setFileType(FileUtil.getSuffix(file.getOriginalFilename())).setCreateBy(SecurityUtils.getUsername())
                .setCreateId(SecurityUtils.getLoginUser().getUser().getUserId().toString())
                .setCreateTime(new Date());
        resourceService.insertSysResource(resource);
        params.put("fileUid", uid);
        params.put("id", id);
        return AjaxResult.success(params);
    }

    /**
     * 文件上传成功触发
     *
     * @param file
     * @return
     */
    public abstract AjaxResult uploadSuccess(AjaxResult result, MultipartFile file);

    /**
     * 删除文件
     * @param id
     */
    public abstract AjaxResult deleteFile(String id);
}
