package com.milelu.service.service.category.impl;

import com.milelu.common.utils.DateUtils;
import com.milelu.service.domain.CategoryTemplate;
import com.milelu.service.mapper.CategoryTemplateMapper;
import com.milelu.service.service.category.CategoryTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类-模板配置Service业务层处理
 *
 * @author MILELU
 * @date 2021-01-20
 */
@Service
public class CategoryTemplateServiceImpl implements CategoryTemplateService
{
    @Autowired
    private CategoryTemplateMapper categoryTemplateMapper;

    /**
     * 查询分类-模板配置
     *
     * @param id 分类-模板配置ID
     * @return 分类-模板配置
     */
    @Override
    public CategoryTemplate selectCategoryTemplateById(String id)
    {
        return categoryTemplateMapper.selectCategoryTemplateById(id);
    }

    /**
     * 查询分类-模板配置列表
     *
     * @param categoryTemplate 分类-模板配置
     * @return 分类-模板配置
     */
    @Override
    public List<CategoryTemplate> selectCategoryTemplateList(CategoryTemplate categoryTemplate)
    {
        return categoryTemplateMapper.selectCategoryTemplateList(categoryTemplate);
    }

    /**
     * 新增分类-模板配置
     *
     * @param categoryTemplate 分类-模板配置
     * @return 结果
     */
    @Override
    public int insertCategoryTemplate(CategoryTemplate categoryTemplate)
    {
        categoryTemplate.setCreateTime(DateUtils.getNowDate());
        return categoryTemplateMapper.insertCategoryTemplate(categoryTemplate);
    }

    /**
     * 修改分类-模板配置
     *
     * @param categoryTemplate 分类-模板配置
     * @return 结果
     */
    @Override
    public int updateCategoryTemplate(CategoryTemplate categoryTemplate)
    {
        categoryTemplate.setUpdateTime(DateUtils.getNowDate());
        return categoryTemplateMapper.updateCategoryTemplate(categoryTemplate);
    }

    /**
     * 批量删除分类-模板配置
     *
     * @param ids 需要删除的分类-模板配置ID
     * @return 结果
     */
    @Override
    public int deleteCategoryTemplateByIds(String[] ids)
    {
        return categoryTemplateMapper.deleteCategoryTemplateByIds(ids);
    }

    /**
     * 删除分类-模板配置信息
     *
     * @param id 分类-模板配置ID
     * @return 结果
     */
    @Override
    public int deleteCategoryTemplateById(String id)
    {
        return categoryTemplateMapper.deleteCategoryTemplateById(id);
    }

    @Override
    public CategoryTemplate getCategoryTemp(String categoryId, String templateId) {
        return  categoryTemplateMapper.getCategoryTemp(categoryId,templateId);
    }

    @Override
    public void deleteByFiled(String field, String id) {
        categoryTemplateMapper.deleteByField(field,id);
    }

    /**
     * 获取模板
     * @param categoryId
     * @return
     */
    @Override
    public String getTempPathByCategoryId(String categoryId) {
        return categoryTemplateMapper.getTempPathByCategoryId(categoryId);
    }
}
