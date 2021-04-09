package com.milelu.service.service.model.impl;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Lists;
import com.milelu.common.config.MileluConfig;
import com.milelu.common.constant.Constants;
import com.milelu.common.core.domain.TreeFileModel;
import com.milelu.common.utils.*;
import com.milelu.common.utils.file.FileLoopUtil;
import com.milelu.common.utils.model.KeyValueModel;
import com.milelu.service.domain.FieldDto;
import com.milelu.service.domain.Model;
import com.milelu.service.domain.ModelTemplate;
import com.milelu.service.mapper.ModelMapper;
import com.milelu.service.service.model.ModelService;
import com.milelu.service.service.model.ModelTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.milelu.common.utils.uuid.SnowflakeIdWorker.getId;

/**
 * // * <p>
 * 服务实现类
 * </p>
 *
 * @author LG
 * @since 2019-10-23
 */
@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    MileluConfig mileluConfig;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ModelTemplateService modelTemplateService;

    /**
     * 查询模型
     *
     * @param id 模型ID
     * @return 模型
     */
    @Override
    public Model selectModelById(String id) {
        return modelMapper.selectModelById(id);
    }

    /**
     * 查询模型列表
     *
     * @param model 模型
     * @return 模型
     */
    @Override
    public List<Model> selectModelList(Model model) {
        return modelMapper.selectModelList(model);
    }

    /**
     * 新增模型
     *
     * @param model 模型
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertModel(Model model) {
        model.setCreateTime(DateUtils.getNowDate()).setId(getId())
                .setCreateId(SecurityUtils.getUserId());
        ModelFieldUtil.filter(model);
        modelMapper.insertModel(model);
        saveModelTemplate(model);
    }

    private void saveModelTemplate(Model model) {
        modelTemplateService.saveModelTemplate(model.getId(),
                model.getTemplatePath().replace("\\",Constants.SEPARATOR)
                        .replace(mileluConfig.getTemplate(), ""));
    }

    /**
     * 修改模型
     *
     * @param model 模型
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateModel(Model model) {
        model.setUpdateTime(DateUtils.getNowDate());
        ModelFieldUtil.filter(model);
        modelMapper.updateModel(model);
        ModelTemplate modelTemplate = modelTemplateService.getModelTemplate(model.getId());
        String templatePath=model.getTemplatePath().replace("\\",Constants.SEPARATOR);
        if (templatePath.startsWith(mileluConfig.getTemplate())){
            System.out.println(templatePath);
            templatePath=templatePath.replace(mileluConfig.getTemplate(),"");
        }
        if (CommonUtils.BeNotNull(modelTemplate)) {
            modelTemplate.setTemplatePath(templatePath);
            modelTemplateService.updateModelTemplate(modelTemplate);
        }else {
            saveModelTemplate(model);
        }
    }

    /**
     * 批量删除模型
     *
     * @param ids 需要删除的模型ID
     * @return 结果
     */
    @Override
    public int deleteModelByIds(String[] ids) {
        return modelMapper.deleteModelByIds(ids);
    }

    /**
     * 删除模型信息
     *
     * @param id 模型ID
     * @return 结果
     */
    @Override
    public int deleteModelById(String id) {
        return modelMapper.deleteModelById(id);
    }

    @Override
    public void saveModel(FieldDto v) {

    }

    @Override
    public void updateModel(FieldDto v) {

    }

    @Override
    public List<TreeFileModel> loadTemplateTree() {
        String template = mileluConfig.getTemplate();
        if (CommonUtils.BeBlank(template) || !FileUtil.exist(template)) {
            return new ArrayList<>();
        }
        List<TreeFileModel> treeFileModels = FileLoopUtil.loopLoadFiles(template, template, new ModelLoopHelp());
        filter(treeFileModels);
        return treeFileModels;
    }

    private void filter(List<TreeFileModel> treeFileModels) {
        //过滤.data结尾的
        treeFileModels.removeIf(
                fileModel -> fileModel.getTitle().endsWith(".data")
        );
        for (TreeFileModel treeFileModel : treeFileModels) {
            if (!treeFileModel.getChildren().isEmpty()) {
                filter(treeFileModel.getChildren());
            } else if (treeFileModel.getChildren().isEmpty()) {
                if (!treeFileModel.getFile().isDirectory()) {
                    treeFileModel.setChildren(null);
                }
            }
        }
    }

    @Override
    public Map<String, Object> loadTemplate() {
        return null;
    }

    @Override
    public FieldDto getById(String pk) {
        return null;
    }

    @Override
    public List<Model> listModel(String categoryId) {
        List<Model> models = modelMapper.listModelByCategoryId(categoryId);
        if(CommonUtils.BeNotEmpty(models)){
            for(Model model:models){
                if(CommonUtils.BeNotBlank(model.getTemplatePath())){
                    String filePath =mileluConfig.getTemplate()+model.getTemplatePath();
                    if(FileUtil.exist(filePath)){
                        model.setMd5Key(Md5.md5(new File(filePath).getAbsolutePath()));
                    }
                }
            }
        }
        return CommonUtils.BeNotEmpty(models)?models: Lists.newArrayList();
    }

    @Override
    public List<KeyValueModel> listPublishModel(String categoryId) {
        List<KeyValueModel> keyValueModels =modelMapper.listPublishModel(categoryId);
        return CommonUtils.BeNotEmpty(keyValueModels)?keyValueModels:Lists.newArrayList();
    }

    @Override
    public String getFormDesign(String modelId) {
        return null;
    }

    @Override
    public Boolean getFormHasFile(String modelId) {
        return null;
    }

    @Override
    public Model getModel(String modelId) {
        return modelMapper.selectModelById(modelId);
    }

    /**
     * 获取明细
     *
     * @param id
     * @return
     */
    @Override
    public Model getDetail(String id) {
        Model model = modelMapper.selectModelById(id);
        if (CommonUtils.BeNotNull(model)) {
            ModelTemplate modelTemplate = modelTemplateService.getModelTemplate(model.getId());
            if (CommonUtils.BeNotNull(modelTemplate)) {
                model.setModelTemplateId(modelTemplate.getId());
                String filePath = mileluConfig.getTemplate() + modelTemplate.getTemplatePath();
                if (FileUtil.exist(filePath) && FileUtil.isFile(filePath)) {
                    model.setMd5Key(Md5.md5(new File(filePath).getAbsolutePath())).setTemplatePath(modelTemplate.getTemplatePath());
                }
            }
        }
        return model;
    }
}
