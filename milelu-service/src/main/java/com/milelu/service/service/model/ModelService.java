package com.milelu.service.service.model;

import com.milelu.common.core.domain.TreeFileModel;
import com.milelu.common.utils.model.KeyValueModel;
import com.milelu.service.domain.FieldDto;
import com.milelu.service.domain.Model;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author LG
 * @since 2019-10-23
 */
public interface ModelService {

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
    public void insertModel(Model model);

    /**
     * 修改模型
     *
     * @param model 模型
     * @return 结果
     */
    public void updateModel(Model model);

    /**
     * 批量删除模型
     *
     * @param ids 需要删除的模型ID
     * @return 结果
     */
    public int deleteModelByIds(String[] ids);

    /**
     * 删除模型信息
     *
     * @param id 模型ID
     * @return 结果
     */
    public int deleteModelById(String id);

    void saveModel(FieldDto v);

    void updateModel(FieldDto v);

    List<TreeFileModel> loadTemplateTree();

    Map<String, Object> loadTemplate();

    FieldDto getById(String pk);

    List<Model> listModel(String categoryId);

    /**
     * 获取分类的发布模型
     *
     * @param categoryId
     * @return
     */
    List<KeyValueModel> listPublishModel(String categoryId);

    String getFormDesign(String modelId);

    Boolean getFormHasFile(String modelId);

    Model getModel(String modelId);

    /**
     * 获取明细
     * @param id
     * @return
     */
    Model getDetail(String id);
}
