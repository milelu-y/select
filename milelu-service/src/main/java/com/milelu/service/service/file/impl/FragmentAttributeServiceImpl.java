package com.milelu.service.service.file.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.milelu.common.constant.Constants;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.exception.CustomException;
import com.milelu.common.utils.CommonUtils;
import com.milelu.common.utils.DateUtils;
import com.milelu.common.utils.ModelFieldUtil;
import com.milelu.common.utils.model.DynamicModel;
import com.milelu.common.utils.uuid.SnowflakeIdWorker;
import com.milelu.service.service.file.FragmentAttributeService;
import com.milelu.service.service.file.FragmentModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.service.mapper.FragmentAttributeMapper;
import com.milelu.service.domain.FragmentAttribute;

/**
 * 页面片段数据Service业务层处理
 *
 * @author MILELU
 * @date 2021-01-11
 */
@Service
public class FragmentAttributeServiceImpl implements FragmentAttributeService {
    @Autowired
    private FragmentAttributeMapper fragmentAttributeMapper;

    @Autowired
    private FragmentModelService fragmentModelService;

    /**
     * 查询页面片段数据
     *
     * @param id 页面片段数据ID
     * @return 页面片段数据
     */
    @Override
    public FragmentAttribute selectFragmentAttributeById(String id) {
        return fragmentAttributeMapper.selectFragmentAttributeById(id);
    }

    /**
     * 查询页面片段数据列表
     *
     * @param fragmentAttribute 页面片段数据
     * @return 页面片段数据
     */
    @Override
    public List<FragmentAttribute> selectFragmentAttributeList(FragmentAttribute fragmentAttribute) {
        if (CommonUtils.BeNotNull(fragmentAttribute)) {
            String id = getId(fragmentAttribute.getFragmentModelId());
            fragmentAttribute.setFragmentModelId(id);
        }
        return fragmentAttributeMapper.getListFragmentAttributeByModelId(fragmentAttribute.getFragmentModelId());
    }

    /**
     * 新增页面片段数据
     *
     * @param fragmentAttribute 页面片段数据
     * @return 结果
     */
    @Override
    public void insertFragmentAttribute(FragmentAttribute fragmentAttribute) {
        if (CommonUtils.BeBlank(fragmentAttribute.getFragmentModelId())) {
            throw new CustomException("页面片段ID不能为空");
        }
        String id = getId(fragmentAttribute.getFragmentModelId());
        fragmentAttribute.setId(SnowflakeIdWorker.getId())
                .setFragmentModelId(id)
                .setCreateTime(DateUtils.getNowDate());
        filterFragmentAttr(fragmentAttribute.getParams(), id, fragmentAttribute);
        fragmentAttributeMapper.insertFragmentAttribute(fragmentAttribute);
    }

    private void filterFragmentAttr(Map<String, Object> params, String id, FragmentAttribute fragmentAttribute) {
        String fieldJsonStr = fragmentModelService.getFragmentFieldListJsonStr(id, 0);
        List<DynamicModel> extendFields = ModelFieldUtil.listExtendModel(fieldJsonStr);
        if (CommonUtils.BeNotEmpty(extendFields)) {
            for (DynamicModel dynamicModel : extendFields) {
                Object data = getMapData(params, dynamicModel.getFieldCode());
                if (CommonUtils.BeNotNull(data)) {
                    dynamicModel.setDefaultValue(data);
                }
            }
            fragmentAttribute.setData(ModelFieldUtil.modelToJsonStr(extendFields));
        }
    }

    private Object getMapData(Map<String, Object> params, String code) {
        if (params.containsKey(code)) {
            Object data = params.get(code);
            if (CommonUtils.BeNotNull(data)) {
                return data;
            } else {
                return "";
            }
        } else {
            return null;
        }
    }

    private String getId(String fileName) {
        if (CommonUtils.BeNotBlank(fileName)) {
            String id = fileName.replace(Constants.DEFAULT_FRAGMENT_PREFIX, "").
                    replace(Constants.DEFAULT_HTML_SUFFIX, "");
            return id;
        }
        return "";
    }

    /**
     * 修改页面片段数据
     *
     * @param fragmentAttribute 页面片段数据
     * @return 结果
     */
    @Override
    public void updateFragmentAttribute(FragmentAttribute fragmentAttribute) {
        if (CommonUtils.BeBlank(fragmentAttribute.getId())) {
            throw new CustomException("页面片段ID不能为空");
        }
        String id = getId(fragmentAttribute.getFragmentModelId());
        fragmentAttribute
                .setFragmentModelId(id)
                .setUpdateTime(DateUtils.getNowDate());
        filterFragmentAttr(fragmentAttribute.getParams(), fragmentAttribute.getFragmentModelId(), fragmentAttribute);
        int i = fragmentAttributeMapper.updateFragmentAttribute(fragmentAttribute);
        System.out.println(i);
    }

    /**
     * 批量删除页面片段数据
     *
     * @param ids 需要删除的页面片段数据ID
     * @return 结果
     */
    @Override
    public int deleteFragmentAttributeByIds(String[] ids) {
        return fragmentAttributeMapper.deleteFragmentAttributeByIds(ids);
    }

    /**
     * 删除页面片段数据信息
     *
     * @param id 页面片段数据ID
     * @return 结果
     */
    @Override
    public int deleteFragmentAttributeById(String id) {
        return fragmentAttributeMapper.deleteFragmentAttributeById(id);
    }

    @Override
    public Map<String, Object> getDesignAttrById(String id) {
        Map<String, Object> result = new HashMap<>();
        List<DynamicModel> resultFields = new ArrayList<>();
        FragmentAttribute attr = fragmentAttributeMapper.selectFragmentAttributeById(id);
        if (CommonUtils.BeNotNull(attr) && CommonUtils.BeNotBlank(attr.getFragmentModelId())) {
            String allFieldStrList = fragmentModelService.getFragmentFieldListJsonStr(attr.getFragmentModelId(), 0);
            if (CommonUtils.BeNotBlank(allFieldStrList)) {
                List<DynamicModel> defaultFields = ModelFieldUtil.listDefaultModel(allFieldStrList);
                if (CommonUtils.BeNotEmpty(defaultFields)) {
                    ModelFieldUtil.setFieldVal(defaultFields, attr, true);
                    resultFields.addAll(defaultFields);
                }
            }
            if (CommonUtils.BeNotBlank(attr.getData())) {
                List<DynamicModel> extendFields = ModelFieldUtil.jsonStrToModel(attr.getData());
                if (CommonUtils.BeNotEmpty(extendFields)) {
                    resultFields.addAll(extendFields);
                }
            }
        }
        result.put("data", attr);
        result.put("models", resultFields);
        return result;
    }

    @Override
    public List<Map> listDataByCode(String code) {
        List<Map> datas = fragmentAttributeMapper.listDataByCode(code);
        filterListMap(datas);
        return CommonUtils.BeNotEmpty(datas)?datas: Lists.newArrayList();
    }

    private void filterListMap(List<Map> maps) {
        if(CommonUtils.BeNotEmpty(maps)){
            for(Map map:maps){
                ModelFieldUtil.formatData(map);
                ModelFieldUtil.formatCover(map,true);
            }
        }
    }
}
