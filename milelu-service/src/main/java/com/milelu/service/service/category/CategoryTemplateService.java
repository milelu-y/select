package com.milelu.service.service.category;

import java.util.List;
import com.milelu.service.domain.CategoryTemplate;

/**
 * 分类-模板配置Service接口
 *
 * @author MILELU
 * @date 2021-01-20
 */
public interface CategoryTemplateService
{
    /**
     * 查询分类-模板配置
     *
     * @param id 分类-模板配置ID
     * @return 分类-模板配置
     */
    public CategoryTemplate selectCategoryTemplateById(String id);

    /**
     * 查询分类-模板配置列表
     *
     * @param categoryTemplate 分类-模板配置
     * @return 分类-模板配置集合
     */
    public List<CategoryTemplate> selectCategoryTemplateList(CategoryTemplate categoryTemplate);

    /**
     * 新增分类-模板配置
     *
     * @param categoryTemplate 分类-模板配置
     * @return 结果
     */
    public int insertCategoryTemplate(CategoryTemplate categoryTemplate);

    /**
     * 修改分类-模板配置
     *
     * @param categoryTemplate 分类-模板配置
     * @return 结果
     */
    public int updateCategoryTemplate(CategoryTemplate categoryTemplate);

    /**
     * 批量删除分类-模板配置
     *
     * @param ids 需要删除的分类-模板配置ID
     * @return 结果
     */
    public int deleteCategoryTemplateByIds(String[] ids);

    /**
     * 删除分类-模板配置信息
     *
     * @param id 分类-模板配置ID
     * @return 结果
     */
    public int deleteCategoryTemplateById(String id);

    /**
     * 获取模板
     * @param id
     * @param templateId
     * @return
     */
    CategoryTemplate getCategoryTemp(String id, String templateId);

    /**
     * 根据字段删除
     * @param field
     * @param id
     */
    void deleteByFiled(String field, String id);

    /**
     * 获取模板
     * @param categoryId
     * @return
     */
    String getTempPathByCategoryId(String categoryId);
}
