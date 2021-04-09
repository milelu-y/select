package com.milelu.service.mapper;

import java.util.List;
import java.util.Map;

import com.milelu.service.domain.CategoryModelRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
 * 分类选择模型时关系 该模型值 tk_model  不是 分类模型Mapper接口
 *
 * @author MILELU
 * @date 2021-01-22
 */
public interface CategoryModelRelationMapper
{
    /**
     * 查询分类选择模型时关系 该模型值 tk_model  不是 分类模型
     *
     * @param id 分类选择模型时关系 该模型值 tk_model  不是 分类模型ID
     * @return 分类选择模型时关系 该模型值 tk_model  不是 分类模型
     */
    public CategoryModelRelation selectCategoryModelRelationById(String id);

    /**
     * 查询分类选择模型时关系 该模型值 tk_model  不是 分类模型列表
     *
     * @param categoryModelRelation 分类选择模型时关系 该模型值 tk_model  不是 分类模型
     * @return 分类选择模型时关系 该模型值 tk_model  不是 分类模型集合
     */
    public List<CategoryModelRelation> selectCategoryModelRelationList(CategoryModelRelation categoryModelRelation);

    /**
     * 新增分类选择模型时关系 该模型值 tk_model  不是 分类模型
     *
     * @param categoryModelRelation 分类选择模型时关系 该模型值 tk_model  不是 分类模型
     * @return 结果
     */
    public int insertCategoryModelRelation(CategoryModelRelation categoryModelRelation);

    /**
     * 修改分类选择模型时关系 该模型值 tk_model  不是 分类模型
     *
     * @param categoryModelRelation 分类选择模型时关系 该模型值 tk_model  不是 分类模型
     * @return 结果
     */
    public int updateCategoryModelRelation(CategoryModelRelation categoryModelRelation);

    /**
     * 删除分类选择模型时关系 该模型值 tk_model  不是 分类模型
     *
     * @param id 分类选择模型时关系 该模型值 tk_model  不是 分类模型ID
     * @return 结果
     */
    public int deleteCategoryModelRelationById(String id);

    /**
     * 批量删除分类选择模型时关系 该模型值 tk_model  不是 分类模型
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCategoryModelRelationByIds(String[] ids);

    void deleteByCSId(String categoryId);

    void deleteByCategoryId(@Param("categoryId") String categoryId);

    String loadTemplatePath(@Param("params") Map<String, Object> params);
}
