package com.milelu.service.service.select.impl;

import java.util.List;

import com.milelu.service.service.select.SelectTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.service.mapper.SelectTemplateMapper;
import com.milelu.service.domain.SelectTemplate;

/**
 * 选版模板Service业务层处理
 *
 * @author MILELU
 * @date 2021-04-09
 */
@Service
public class SelectTemplateServiceImpl implements SelectTemplateService
{
    @Autowired
    private SelectTemplateMapper selectTemplateMapper;

    /**
     * 查询选版模板
     *
     * @param id 选版模板ID
     * @return 选版模板
     */
    @Override
    public SelectTemplate selectSelectTemplateById(Long id)
    {
        return selectTemplateMapper.selectSelectTemplateById(id);
    }

    /**
     * 查询选版模板列表
     *
     * @param selectTemplate 选版模板
     * @return 选版模板
     */
    @Override
    public List<SelectTemplate> selectSelectTemplateList(SelectTemplate selectTemplate)
    {
        return selectTemplateMapper.selectSelectTemplateList(selectTemplate);
    }

    /**
     * 新增选版模板
     *
     * @param selectTemplate 选版模板
     * @return 结果
     */
    @Override
    public int insertSelectTemplate(SelectTemplate selectTemplate)
    {
        return selectTemplateMapper.insertSelectTemplate(selectTemplate);
    }

    /**
     * 修改选版模板
     *
     * @param selectTemplate 选版模板
     * @return 结果
     */
    @Override
    public int updateSelectTemplate(SelectTemplate selectTemplate)
    {
        return selectTemplateMapper.updateSelectTemplate(selectTemplate);
    }

    /**
     * 批量删除选版模板
     *
     * @param ids 需要删除的选版模板ID
     * @return 结果
     */
    @Override
    public int deleteSelectTemplateByIds(Long[] ids)
    {
        return selectTemplateMapper.deleteSelectTemplateByIds(ids);
    }

    /**
     * 删除选版模板信息
     *
     * @param id 选版模板ID
     * @return 结果
     */
    @Override
    public int deleteSelectTemplateById(Long id)
    {
        return selectTemplateMapper.deleteSelectTemplateById(id);
    }
}
