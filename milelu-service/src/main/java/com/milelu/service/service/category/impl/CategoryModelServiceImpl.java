package com.milelu.service.service.category.impl;

import java.util.List;
import com.milelu.common.utils.DateUtils;
import com.milelu.common.utils.SecurityUtils;
import com.milelu.service.service.category.CategoryModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.service.mapper.CategoryModelMapper;
import com.milelu.service.domain.CategoryModel;

import static com.milelu.common.utils.uuid.SnowflakeIdWorker.getId;

/**
 * 分类模型Service业务层处理
 *
 * @author MILELU
 * @date 2021-01-19
 */
@Service
public class CategoryModelServiceImpl implements CategoryModelService
{
    @Autowired
    private CategoryModelMapper categoryModelMapper;

    /**
     * 查询分类模型
     *
     * @param id 分类模型ID
     * @return 分类模型
     */
    @Override
    public CategoryModel selectCategoryModelById(String id)
    {
        return categoryModelMapper.selectCategoryModelById(id);
    }

    /**
     * 查询分类模型列表
     *
     * @param categoryModel 分类模型
     * @return 分类模型
     */
    @Override
    public List<CategoryModel> selectCategoryModelList(CategoryModel categoryModel)
    {
        return categoryModelMapper.selectCategoryModelList(categoryModel);
    }

    /**
     * 新增分类模型
     *
     * @param categoryModel 分类模型
     * @return 结果
     */
    @Override
    public int insertCategoryModel(CategoryModel categoryModel)
    {
        categoryModel.setId(getId()).setCreateId(SecurityUtils
                .getLoginUser().getUser().getUserId().toString())
                .setCreateTime(DateUtils.getNowDate());
        return categoryModelMapper.insertCategoryModel(categoryModel);
    }

    /**
     * 修改分类模型
     *
     * @param categoryModel 分类模型
     * @return 结果
     */
    @Override
    public int updateCategoryModel(CategoryModel categoryModel)
    {
        categoryModel.setUpdateTime(DateUtils.getNowDate());
        return categoryModelMapper.updateCategoryModel(categoryModel);
    }

    /**
     * 批量删除分类模型
     *
     * @param ids 需要删除的分类模型ID
     * @return 结果
     */
    @Override
    public int deleteCategoryModelByIds(String[] ids)
    {
        return categoryModelMapper.deleteCategoryModelByIds(ids);
    }

    /**
     * 删除分类模型信息
     *
     * @param id 分类模型ID
     * @return 结果
     */
    @Override
    public int deleteCategoryModelById(String id)
    {
        return categoryModelMapper.deleteCategoryModelById(id);
    }

    /**
     * 获取字段
     * @param categoryModelId
     * @return
     */
    @Override
    public String getDesignField(String categoryModelId) {
        return categoryModelMapper.getDesignField(categoryModelId);
    }

    /**
     * 根据字段删除
     * @param field
     * @param id
     */
    @Override
    public void deleteByFiled(String field, String id) {
        categoryModelMapper.deleteByFiled(field,id);
    }
}
