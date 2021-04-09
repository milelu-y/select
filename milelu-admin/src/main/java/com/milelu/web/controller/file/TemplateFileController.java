package com.milelu.web.controller.file;

import com.milelu.common.annotation.Log;
import com.milelu.common.annotation.ResponseResult;
import com.milelu.common.config.MileluConfig;
import com.milelu.common.constant.Constants;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.core.domain.Metadata;
import com.milelu.common.core.domain.TreeFileInfo;
import com.milelu.common.enums.BusinessType;
import com.milelu.common.exception.file.FileNameLengthLimitExceededException;
import com.milelu.common.utils.CommonUtils;
import com.milelu.common.utils.StringUtils;
import com.milelu.common.utils.file.FileUploadUtils;
import com.milelu.common.utils.file.MimeTypeUtils;
import com.milelu.framework.config.ServerConfig;
import com.milelu.service.service.file.TemplateFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.milelu.common.utils.file.FileUploadUtils.*;

/**
 * @author MILELU
 * @date 2020/12/17 9:58
 * 模板文件管理控制器
 */
@RestController
@RequestMapping("/file/template")
public class TemplateFileController {


    @Autowired
    private TemplateFileService templateFileService;

    @Autowired
    private ServerConfig serverConfig;

    @GetMapping("/getSingleTemplateMetadataByPath")
    @ResponseResult(isAjaxResult = true)
    public Metadata getSingleTemplateMetadataByPath(@NotBlank String path) {
        try {
            return templateFileService.getSingleTemplateMetadataByPath(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/getTemplateTree")
    public TreeFileInfo getTemplateTree() {
        return templateFileService.TemplateTree();
    }

    @GetMapping("/getTemplateContentByPath")
    public AjaxResult getTemplateContentByPath(@NotBlank String path) {
        return templateFileService.getContent(path);
    }

    @Log(title = "修改模板内容", businessType = BusinessType.UPDATE)
    @PutMapping
    @ResponseResult(isAjaxResult = true)
    public void updateTemplateContent(@RequestBody Map<String, String> map) {
        templateFileService.updateTemplateContent(map.get("path"), map.get("content"));
    }

    @Log(title = "新增模板内容", businessType = BusinessType.INSERT)
    @PostMapping
    @ResponseResult(isAjaxResult = true)
    public void addTemplate(@RequestBody Metadata metadata) {
        templateFileService.addTemplate(metadata);
    }

    @Log(title = "修改模板元数据", businessType = BusinessType.INSERT)
    @PutMapping("/uploadSaveTemplate")
    @ResponseResult(isAjaxResult = true)
    public void updateTemplate(@RequestBody Metadata metadata) {
        templateFileService.updateTemplateMetadata(metadata);
    }

    @Log(title = "删除模板", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteTemplate")
    public AjaxResult deleteTemplate(@RequestParam("path") String path) {
        return templateFileService.deleteTemplate(path);
    }

    /**
     * 模板上传上传请求
     */
    @Log(title = "上传模板", businessType = BusinessType.OTHER)
    @PostMapping("/upload")
    public AjaxResult uploadFile(MultipartFile file, String path) throws Exception {
        try {
            // 上传文件路径
            String filePath = MileluConfig.getWebTemplateFilePath();

            if (CommonUtils.isNotEmptyString(path)) {
                filePath += path;
            }
            // 上传并返回新文件名称
            int fileNamelength = file.getOriginalFilename().length();
            if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
                throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
            }

            assertAllowed(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);

            String fileName = file.getOriginalFilename();
            File desc = getAbsoluteFile(filePath, fileName);
            file.transferTo(desc);
            String pathFileName = getPathFileName(filePath, fileName);
            String url = serverConfig.getUrl() + pathFileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 获取绝对路径
     *
     * @param uploadDir
     * @param fileName
     * @return
     * @throws IOException
     */
    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }

    /**
     * 获取文件路径
     *
     * @param uploadDir
     * @param fileName
     * @return
     * @throws IOException
     */
    private static final String getPathFileName(String uploadDir, String fileName) throws IOException {
        int dirLastIndex = MileluConfig.getProfile().length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        String pathFileName = Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
        return pathFileName;
    }

    /**
     * 生成模板
     */
    @PostMapping("/generateTemplate")
    @ResponseResult(isAjaxResult = true)
    public void generateTemplate(@RequestBody Map<String, Object> map){
        System.out.println(map.toString());
        templateFileService.generateTemplate(map);
    }
}
