package com.milelu.service.service.category;

import java.util.List;
import com.milelu.service.domain.CategoryAttribute;

/**
 * 分类扩展Service接口
 *
 * @author MILELU
 * @date 2021-01-20
 */
public interface CategoryAttributeService
{
    /**
     * 查询分类扩展
     *
     * @param categoryId 分类扩展ID
     * @return 分类扩展
     */
    public CategoryAttribute selectCategoryAttributeById(Long categoryId);

    /**
     * 查询分类扩展列表
     *
     * @param categoryAttribute 分类扩展
     * @return 分类扩展集合
     */
    public List<CategoryAttribute> selectCategoryAttributeList(CategoryAttribute categoryAttribute);

    /**
     * 新增分类扩展
     *
     * @param categoryAttribute 分类扩展
     * @return 结果
     */
    public int insertCategoryAttribute(CategoryAttribute categoryAttribute);

    /**
     * 修改分类扩展
     *
     * @param categoryAttribute 分类扩展
     * @return 结果
     */
    public int updateCategoryAttribute(CategoryAttribute categoryAttribute);

    /**
     * 批量删除分类扩展
     *
     * @param categoryIds 需要删除的分类扩展ID
     * @return 结果
     */
    public int deleteCategoryAttributeByIds(Long[] categoryIds);

    /**
     * 删除分类扩展信息
     *
     * @param categoryId 分类扩展ID
     * @return 结果
     */
    public int deleteCategoryAttributeById(Long categoryId);

    /**
     * 根据分类ID获取SEO信息
     * @param categoryId
     */
    CategoryAttribute getSeo(String categoryId);

    /**
     * 根据分类ID修改Attribute
     * @param categoryAttribute
     */
    void updateAttributeSeo(CategoryAttribute categoryAttribute);

    /**
     * 根据字段删除
     * @param field
     * @param id
     */
    void deleteByFiled(String field, String id);
}
