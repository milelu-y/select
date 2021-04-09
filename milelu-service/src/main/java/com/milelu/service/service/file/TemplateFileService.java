package com.milelu.service.service.file;

import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.core.domain.Metadata;
import com.milelu.common.core.domain.TreeFileInfo;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * @author MILELU
 * @date 2020/12/17 10:03
 */

public interface TemplateFileService {
    /**
     * 获取模板文件树
     * @return
     */
    TreeFileInfo TemplateTree();

    /**
     * 根据路径获取内容
     * @param path
     * @return
     */
    AjaxResult getContent(String path);

    /**
     * 根据路径更新模板内容
     * @param path
     * @param content
     */
    void updateTemplateContent(String path, String content);

    /**
     * 根据路径删除内容
     * @param path
     * @return
     */
    AjaxResult deleteTemplate(String path);

    /**
     * 新增模板
     * @param metadata
     */
    void addTemplate(Metadata metadata);


    /**
     * 根据路径获取模板元数据
     * @param path
     * @return
     */
    Metadata getSingleTemplateMetadataByPath(String path) throws FileNotFoundException;

    /**
     * 修改模板元数据
     * @param metadata
     */
    void updateTemplateMetadata(Metadata metadata);

    /**
     * 生成模板
     * @param map
     */
    void generateTemplate(Map<String, Object> map);
}
