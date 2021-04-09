package com.milelu.service.mapper;

import java.util.List;

import com.milelu.common.utils.model.KeyValueModel;
import com.milelu.service.domain.Model;

/**
 * 模型Mapper接口
 *
 * @author MILELU
 * @date 2021-01-21
 */
public interface ModelMapper
{
    /**
     * 查询模型
     *
     * @param id 模型ID
     * @return 模型
     */
    public Model selectModelById(String id);

    /**
     * 查询模型列表
     *
     * @param model 模型
     * @return 模型集合
     */
    public List<Model> selectModelList(Model model);

    /**
     * 新增模型
     *
     * @param model 模型
     * @return 结果
     */
    public int insertModel(Model model);

    /**
     * 修改模型
     *
     * @param model 模型
     * @return 结果
     */
    public int updateModel(Model model);

    /**
     * 删除模型
     *
     * @param id 模型ID
     * @return 结果
     */
    public int deleteModelById(String id);

    /**
     * 批量删除模型
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteModelByIds(String[] ids);

    /**
     * 根据分类id查询
     * @param categoryId
     * @return
     */
    List<Model> listModelByCategoryId(String categoryId);


    List<KeyValueModel> listPublishModel(String categoryId);
}
