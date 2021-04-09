package com.milelu.service.service.category.impl;

import java.util.List;

import com.milelu.service.service.category.CategoryAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.service.mapper.CategoryAttributeMapper;
import com.milelu.service.domain.CategoryAttribute;

/**
 * 分类扩展Service业务层处理
 *
 * @author MILELU
 * @date 2021-01-20
 */
@Service
public class CategoryAttributeServiceImpl implements CategoryAttributeService
{
    @Autowired
    private CategoryAttributeMapper categoryAttributeMapper;

    /**
     * 查询分类扩展
     *
     * @param categoryId 分类扩展ID
     * @return 分类扩展
     */
    @Override
    public CategoryAttribute selectCategoryAttributeById(Long categoryId)
    {
        return categoryAttributeMapper.selectCategoryAttributeById(categoryId);
    }

    /**
     * 查询分类扩展列表
     *
     * @param categoryAttribute 分类扩展
     * @return 分类扩展
     */
    @Override
    public List<CategoryAttribute> selectCategoryAttributeList(CategoryAttribute categoryAttribute)
    {
        return categoryAttributeMapper.selectCategoryAttributeList(categoryAttribute);
    }

    /**
     * 新增分类扩展
     *
     * @param categoryAttribute 分类扩展
     * @return 结果
     */
    @Override
    public int insertCategoryAttribute(CategoryAttribute categoryAttribute)
    {
        return categoryAttributeMapper.insertCategoryAttribute(categoryAttribute);
    }

    /**
     * 修改分类扩展
     *
     * @param categoryAttribute 分类扩展
     * @return 结果
     */
    @Override
    public int updateCategoryAttribute(CategoryAttribute categoryAttribute)
    {
        return categoryAttributeMapper.updateCategoryAttribute(categoryAttribute);
    }

    /**
     * 批量删除分类扩展
     *
     * @param categoryIds 需要删除的分类扩展ID
     * @return 结果
     */
    @Override
    public int deleteCategoryAttributeByIds(Long[] categoryIds)
    {
        return categoryAttributeMapper.deleteCategoryAttributeByIds(categoryIds);
    }

    /**
     * 删除分类扩展信息
     *
     * @param categoryId 分类扩展ID
     * @return 结果
     */
    @Override
    public int deleteCategoryAttributeById(Long categoryId)
    {
        return categoryAttributeMapper.deleteCategoryAttributeById(categoryId);
    }

    /**
     * 根据分类ID获取SEO信息
     * @param categoryId
     */
    @Override
    public CategoryAttribute getSeo(String categoryId) {
        CategoryAttribute categoryAttribute = categoryAttributeMapper.getSeo(categoryId);
        return categoryAttribute;
    }

    /**
     * 根据分类ID修改Attribute
     * @param categoryAttribute
     */
    @Override
    public void updateAttributeSeo(CategoryAttribute categoryAttribute) {
        updateCategoryAttribute(categoryAttribute);
    }

    @Override
    public void deleteByFiled(String field, String id) {
        categoryAttributeMapper.deleteByFiled(field,id);
    }
}
