package com.milelu.service.service.select.impl;

import java.util.List;

import com.milelu.service.service.select.SelectCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.service.mapper.SelectCategoryMapper;
import com.milelu.service.domain.SelectCategory;

/**
 * 选版-分类Service业务层处理
 *
 * @author MILELU
 * @date 2021-04-09
 */
@Service
public class SelectCategoryServiceImpl implements SelectCategoryService
{
    @Autowired
    private SelectCategoryMapper selectCategoryMapper;

    /**
     * 查询选版-分类
     *
     * @param id 选版-分类ID
     * @return 选版-分类
     */
    @Override
    public SelectCategory selectSelectCategoryById(Long id)
    {
        return selectCategoryMapper.selectSelectCategoryById(id);
    }

    /**
     * 查询选版-分类列表
     *
     * @param selectCategory 选版-分类
     * @return 选版-分类
     */
    @Override
    public List<SelectCategory> selectSelectCategoryList(SelectCategory selectCategory)
    {
        return selectCategoryMapper.selectSelectCategoryList(selectCategory);
    }

    /**
     * 新增选版-分类
     *
     * @param selectCategory 选版-分类
     * @return 结果
     */
    @Override
    public int insertSelectCategory(SelectCategory selectCategory)
    {
        return selectCategoryMapper.insertSelectCategory(selectCategory);
    }

    /**
     * 修改选版-分类
     *
     * @param selectCategory 选版-分类
     * @return 结果
     */
    @Override
    public int updateSelectCategory(SelectCategory selectCategory)
    {
        return selectCategoryMapper.updateSelectCategory(selectCategory);
    }

    /**
     * 批量删除选版-分类
     *
     * @param ids 需要删除的选版-分类ID
     * @return 结果
     */
    @Override
    public int deleteSelectCategoryByIds(Long[] ids)
    {
        return selectCategoryMapper.deleteSelectCategoryByIds(ids);
    }

    /**
     * 删除选版-分类信息
     *
     * @param id 选版-分类ID
     * @return 结果
     */
    @Override
    public int deleteSelectCategoryById(Long id)
    {
        return selectCategoryMapper.deleteSelectCategoryById(id);
    }
}
