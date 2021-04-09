package com.milelu.service.service.model;

import java.util.List;
import com.milelu.service.domain.ModelTemplate;

/**
 * 模型模板针对不同的站点不同的模型下有不同的 模板页面Service接口
 *
 * @author MILELU
 * @date 2021-01-21
 */
public interface ModelTemplateService
{
    /**
     * 查询模型模板针对不同的站点不同的模型下有不同的 模板页面
     *
     * @param id 模型模板针对不同的站点不同的模型下有不同的 模板页面ID
     * @return 模型模板针对不同的站点不同的模型下有不同的 模板页面
     */
    public ModelTemplate selectModelTemplateById(String id);

    /**
     * 查询模型模板针对不同的站点不同的模型下有不同的 模板页面列表
     *
     * @param modelTemplate 模型模板针对不同的站点不同的模型下有不同的 模板页面
     * @return 模型模板针对不同的站点不同的模型下有不同的 模板页面集合
     */
    public List<ModelTemplate> selectModelTemplateList(ModelTemplate modelTemplate);

    /**
     * 新增模型模板针对不同的站点不同的模型下有不同的 模板页面
     *
     * @param modelTemplate 模型模板针对不同的站点不同的模型下有不同的 模板页面
     * @return 结果
     */
    public int insertModelTemplate(ModelTemplate modelTemplate);

    /**
     * 修改模型模板针对不同的站点不同的模型下有不同的 模板页面
     *
     * @param modelTemplate 模型模板针对不同的站点不同的模型下有不同的 模板页面
     * @return 结果
     */
    public int updateModelTemplate(ModelTemplate modelTemplate);

    /**
     * 批量删除模型模板针对不同的站点不同的模型下有不同的 模板页面
     *
     * @param ids 需要删除的模型模板针对不同的站点不同的模型下有不同的 模板页面ID
     * @return 结果
     */
    public int deleteModelTemplateByIds(String[] ids);

    /**
     * 删除模型模板针对不同的站点不同的模型下有不同的 模板页面信息
     *
     * @param id 模型模板针对不同的站点不同的模型下有不同的 模板页面ID
     * @return 结果
     */
    public int deleteModelTemplateById(String id);

    /**
     * 保存
     * @param modelId
     * @param templatePath
     */
    void saveModelTemplate(String modelId,String templatePath);

    /**
     * 根据模型id获取模型模板
     * @param modelId
     * @return
     */
    ModelTemplate getModelTemplate(String modelId);
}
