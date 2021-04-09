package com.milelu.service.service.model.impl;

import java.util.List;

import com.milelu.common.utils.DateUtils;
import com.milelu.common.utils.SecurityUtils;
import com.milelu.service.service.model.ModelTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.service.mapper.ModelTemplateMapper;
import com.milelu.service.domain.ModelTemplate;

import static com.milelu.common.utils.uuid.SnowflakeIdWorker.getId;

/**
 * 模型模板针对不同的站点不同的模型下有不同的 模板页面Service业务层处理
 *
 * @author MILELU
 * @date 2021-01-21
 */
@Service
public class ModelTemplateServiceImpl implements ModelTemplateService {
    @Autowired
    private ModelTemplateMapper modelTemplateMapper;

    /**
     * 查询模型模板针对不同的站点不同的模型下有不同的 模板页面
     *
     * @param id 模型模板针对不同的站点不同的模型下有不同的 模板页面ID
     * @return 模型模板针对不同的站点不同的模型下有不同的 模板页面
     */
    @Override
    public ModelTemplate selectModelTemplateById(String id) {
        return modelTemplateMapper.selectModelTemplateById(id);
    }

    /**
     * 查询模型模板针对不同的站点不同的模型下有不同的 模板页面列表
     *
     * @param modelTemplate 模型模板针对不同的站点不同的模型下有不同的 模板页面
     * @return 模型模板针对不同的站点不同的模型下有不同的 模板页面
     */
    @Override
    public List<ModelTemplate> selectModelTemplateList(ModelTemplate modelTemplate) {
        return modelTemplateMapper.selectModelTemplateList(modelTemplate);
    }

    /**
     * 新增模型模板针对不同的站点不同的模型下有不同的 模板页面
     *
     * @param modelTemplate 模型模板针对不同的站点不同的模型下有不同的 模板页面
     * @return 结果
     */
    @Override
    public int insertModelTemplate(ModelTemplate modelTemplate) {
        modelTemplate.setId(getId())
                .setCreateTime(DateUtils.getNowDate()).setCreateId(SecurityUtils.getUserId());
        return modelTemplateMapper.insertModelTemplate(modelTemplate);
    }

    /**
     * 修改模型模板针对不同的站点不同的模型下有不同的 模板页面
     *
     * @param modelTemplate 模型模板针对不同的站点不同的模型下有不同的 模板页面
     * @return 结果
     */
    @Override
    public int updateModelTemplate(ModelTemplate modelTemplate) {
        modelTemplate.setUpdateTime(DateUtils.getNowDate());
        return modelTemplateMapper.updateModelTemplate(modelTemplate);
    }

    /**
     * 批量删除模型模板针对不同的站点不同的模型下有不同的 模板页面
     *
     * @param ids 需要删除的模型模板针对不同的站点不同的模型下有不同的 模板页面ID
     * @return 结果
     */
    @Override
    public int deleteModelTemplateByIds(String[] ids) {
        return modelTemplateMapper.deleteModelTemplateByIds(ids);
    }

    /**
     * 删除模型模板针对不同的站点不同的模型下有不同的 模板页面信息
     *
     * @param id 模型模板针对不同的站点不同的模型下有不同的 模板页面ID
     * @return 结果
     */
    @Override
    public int deleteModelTemplateById(String id) {
        return modelTemplateMapper.deleteModelTemplateById(id);
    }

    /**
     * 保存
     */
    @Override
    public void saveModelTemplate(String modelId, String templatePath) {
        ModelTemplate modelTemplate = new ModelTemplate();
        modelTemplate.setModelId(modelId)
                .setTemplateId("1234567890").setTemplatePath(templatePath);
        insertModelTemplate(modelTemplate);
    }

    /**
     * 根据模型id获取模型模板
     * @param modelId
     * @return
     */
    @Override
    public ModelTemplate getModelTemplate(String modelId) {
        return modelTemplateMapper.getModelTemplate(modelId);
    }
}
