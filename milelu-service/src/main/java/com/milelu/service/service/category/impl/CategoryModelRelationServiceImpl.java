package com.milelu.service.service.category.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.exception.CustomException;
import com.milelu.common.utils.CommonUtils;
import com.milelu.common.utils.DateUtils;
import com.milelu.common.utils.SecurityUtils;
import com.milelu.common.utils.model.ValidList;
import com.milelu.service.service.category.CategoryModelRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.service.mapper.CategoryModelRelationMapper;
import com.milelu.service.domain.CategoryModelRelation;
import org.springframework.transaction.annotation.Transactional;

import static com.milelu.common.utils.uuid.SnowflakeIdWorker.getId;

/**
 * 分类选择模型时关系 该模型值 tk_model  不是 分类模型Service业务层处理
 *
 * @author MILELU
 * @date 2021-01-22
 */
@Service
public class CategoryModelRelationServiceImpl implements CategoryModelRelationService
{
    @Autowired
    private CategoryModelRelationMapper categoryModelRelationMapper;

    /**
     * 查询分类选择模型时关系 该模型值 tk_model  不是 分类模型
     *
     * @param id 分类选择模型时关系 该模型值 tk_model  不是 分类模型ID
     * @return 分类选择模型时关系 该模型值 tk_model  不是 分类模型
     */
    @Override
    public CategoryModelRelation selectCategoryModelRelationById(String id)
    {
        return categoryModelRelationMapper.selectCategoryModelRelationById(id);
    }

    /**
     * 查询分类选择模型时关系 该模型值 tk_model  不是 分类模型列表
     *
     * @param categoryModelRelation 分类选择模型时关系 该模型值 tk_model  不是 分类模型
     * @return 分类选择模型时关系 该模型值 tk_model  不是 分类模型
     */
    @Override
    public List<CategoryModelRelation> selectCategoryModelRelationList(CategoryModelRelation categoryModelRelation)
    {
        return categoryModelRelationMapper.selectCategoryModelRelationList(categoryModelRelation);
    }

    /**
     * 新增分类选择模型时关系 该模型值 tk_model  不是 分类模型
     *
     * @param categoryModelRelation 分类选择模型时关系 该模型值 tk_model  不是 分类模型
     * @return 结果
     */
    @Override
    public int insertCategoryModelRelation(CategoryModelRelation categoryModelRelation)
    {
        categoryModelRelation.setCreateTime(DateUtils.getNowDate());
        return categoryModelRelationMapper.insertCategoryModelRelation(categoryModelRelation);
    }

    /**
     * 修改分类选择模型时关系 该模型值 tk_model  不是 分类模型
     *
     * @param categoryModelRelation 分类选择模型时关系 该模型值 tk_model  不是 分类模型
     * @return 结果
     */
    @Override
    public int updateCategoryModelRelation(CategoryModelRelation categoryModelRelation)
    {
        categoryModelRelation.setUpdateTime(DateUtils.getNowDate());
        return categoryModelRelationMapper.updateCategoryModelRelation(categoryModelRelation);
    }

    /**
     * 批量删除分类选择模型时关系 该模型值 tk_model  不是 分类模型
     *
     * @param ids 需要删除的分类选择模型时关系 该模型值 tk_model  不是 分类模型ID
     * @return 结果
     */
    @Override
    public int deleteCategoryModelRelationByIds(String[] ids)
    {
        return categoryModelRelationMapper.deleteCategoryModelRelationByIds(ids);
    }

    /**
     * 删除分类选择模型时关系 该模型值 tk_model  不是 分类模型信息
     *
     * @param id 分类选择模型时关系 该模型值 tk_model  不是 分类模型ID
     * @return 结果
     */
    @Override
    public int deleteCategoryModelRelationById(String id)
    {
        return categoryModelRelationMapper.deleteCategoryModelRelationById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(ValidList<CategoryModelRelation> vs) {
        if(CommonUtils.BeNotEmpty(vs)){
            checkIsLegal(vs);
            categoryModelRelationMapper.deleteByCSId(vs.get(0).getCategoryId());
            saveOrUpdate(vs,"1234567890");
        }
    }

    @Override
    public void deleteByFiled(String f, String id) {
        categoryModelRelationMapper.deleteByCategoryId(id);
    }

    @Override
    public String loadTemplatePath(Map<String, Object> params) {

        return categoryModelRelationMapper.loadTemplatePath(params);
    }

    private void saveOrUpdate(List<CategoryModelRelation> vs,String templateId){
        Date nowDate = DateUtils.getNowDate();
        if(CommonUtils.BeNotEmpty(vs)){
            for(CategoryModelRelation v:vs){
                v.setTemplateId(templateId).setId(getId()).setCreateId(SecurityUtils.getUserId())
                        .setCreateTime(nowDate);
                insertCategoryModelRelation(v);
            }
        }
    }


    private void checkIsLegal(List<CategoryModelRelation> vs){
        for(CategoryModelRelation mr:vs){
            if(CommonUtils.BeBlank(mr.getCategoryId())){
                throw new CustomException("栏目ID不能为空");
            }
            if(CommonUtils.BeBlank(mr.getTemplatePath())){
                throw new CustomException("模板地址不能为空");
            }
            mr.setTemplatePath(mr.getTemplatePath().replace("\\","/"));
        }
    }
}
