package com.milelu.service.mapper;

import java.util.List;
import com.milelu.service.domain.SelectCategory;

/**
 * 选版-分类Mapper接口
 * 
 * @author MILELU
 * @date 2021-04-09
 */
public interface SelectCategoryMapper 
{
    /**
     * 查询选版-分类
     * 
     * @param id 选版-分类ID
     * @return 选版-分类
     */
    public SelectCategory selectSelectCategoryById(Long id);

    /**
     * 查询选版-分类列表
     * 
     * @param selectCategory 选版-分类
     * @return 选版-分类集合
     */
    public List<SelectCategory> selectSelectCategoryList(SelectCategory selectCategory);

    /**
     * 新增选版-分类
     * 
     * @param selectCategory 选版-分类
     * @return 结果
     */
    public int insertSelectCategory(SelectCategory selectCategory);

    /**
     * 修改选版-分类
     * 
     * @param selectCategory 选版-分类
     * @return 结果
     */
    public int updateSelectCategory(SelectCategory selectCategory);

    /**
     * 删除选版-分类
     * 
     * @param id 选版-分类ID
     * @return 结果
     */
    public int deleteSelectCategoryById(Long id);

    /**
     * 批量删除选版-分类
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSelectCategoryByIds(Long[] ids);
}
