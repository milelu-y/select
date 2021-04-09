package com.milelu.service.mapper;

import java.util.List;
import com.milelu.service.domain.CategoryModel;
import org.apache.ibatis.annotations.Param;

/**
 * 分类模型Mapper接口
 *
 * @author MILELU
 * @date 2021-01-19
 */
public interface CategoryModelMapper
{
    /**
     * 查询分类模型
     *
     * @param id 分类模型ID
     * @return 分类模型
     */
    public CategoryModel selectCategoryModelById(String id);

    /**
     * 查询分类模型列表
     *
     * @param categoryModel 分类模型
     * @return 分类模型集合
     */
    public List<CategoryModel> selectCategoryModelList(CategoryModel categoryModel);

    /**
     * 新增分类模型
     *
     * @param categoryModel 分类模型
     * @return 结果
     */
    public int insertCategoryModel(CategoryModel categoryModel);

    /**
     * 修改分类模型
     *
     * @param categoryModel 分类模型
     * @return 结果
     */
    public int updateCategoryModel(CategoryModel categoryModel);

    /**
     * 删除分类模型
     *
     * @param id 分类模型ID
     * @return 结果
     */
    public int deleteCategoryModelById(String id);

    /**
     * 批量删除分类模型
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCategoryModelByIds(String[] ids);

    /**
     * 获取字段
     * @param id
     * @return
     */
    String getDesignField(String id);

    /**
     *
     * @param field
     * @param id
     */
    void deleteByFiled(@Param("field") Object field, @Param("id") String id);
}
