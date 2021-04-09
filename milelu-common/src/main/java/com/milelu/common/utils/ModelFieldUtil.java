package com.milelu.common.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.milelu.common.constant.Constants;
import com.milelu.common.core.domain.BaseEntity;
import com.milelu.common.enums.FilterField;
import com.milelu.common.enums.InputTypeEnum;
import com.milelu.common.utils.iterator.BeanPropertyBox;
import com.milelu.common.utils.iterator.BeanPropertyIterator;
import com.milelu.common.utils.iterator.Iterator;
import com.milelu.common.utils.model.BaseField;
import com.milelu.common.utils.model.DynamicModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author MILELU
 * 构建模型字段工具类
 * @date 2021/1/9 16:04
 */
public class ModelFieldUtil {
    private static final String COVER = "cover";

    private static final String DATA = "data";


    public static List<DynamicModel> loadModel(FilterField fragment) {
        List<DynamicModel> dynamicModels = loadModel();

        if(CommonUtils.BeNotEmpty(dynamicModels)){
            dynamicModels=  dynamicModels.stream().filter(s->s.getFilterField().contains(fragment.getCode())).collect(Collectors.toList());
        }
        return dynamicModels;
    }

    private static List<DynamicModel> loadModel() {
        //加载外部化Json文件
        Resource resource = new ClassPathResource("model.json");
        if(resource.exists()){
            try {
                //读取Json文件内容
                String jsonStr = FileUtil.readString(resource.getFile(), Constants.UTF8);
                //内容转换
                List<DynamicModel> models = toList(jsonStr,DynamicModel.class);
                return CommonUtils.BeNotEmpty(models)?models: new ArrayList<>();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    /**
     * 转换内容
     * @param jsonStr
     * @param tClass
     * @param <T>
     * @return
     */
    private static <T> List<T> toList(String jsonStr,Class<T> tClass){
        List<T> models = JSONUtil.toList(JSONUtil.parseArray(jsonStr),tClass);
        return models;
    }

    public static void filter(BaseField field) {
        List<DynamicModel> defaultFieldList = toList(field.getDefaultFieldList(), DynamicModel.class);
        List<DynamicModel> extendFieldList = toList(field.getExtendFieldList(), DynamicModel.class);
        extendFilter(extendFieldList);
        List<DynamicModel> allField  = new ArrayList<>();
        allField.addAll(extendFieldList);
        if (CommonUtils.BeNotEmpty(defaultFieldList)){
            Map<String,String> fieldTextMap =new HashMap<>(16);
            List<String> requireFiled= new ArrayList<>();
            for (DynamicModel model : defaultFieldList) {
                fieldTextMap.put(model.getFieldCode(),model.getFieldAliase());
                if(model.getIsRequired()){
                    requireFiled.add(model.getFieldCode());
                }
                if(model.getCheck()){
                    allField.add(model);
                }
            }
            field.setFieldTextMap(toJsonStr(fieldTextMap)).setRequiredFieldList(toJsonStr(requireFiled)).setExtendFieldList(toJsonStr(extendFieldList))
                    .setAllFieldList(toJsonStr(allField));
        }
    }

    private static void extendFilter(List<DynamicModel> extendFieldList) {
        if(CommonUtils.BeNotEmpty(extendFieldList)){
            for(DynamicModel dynamicModel:extendFieldList){
                dynamicModel.setFieldAliase(dynamicModel.getFieldName());
            }
        }
    }
    private static String toJsonStr(Object object){
        return JSONUtil.toJsonStr(object);
    }

    public static List<DynamicModel> jsonStrToModel(String jsonStr){
        if(CommonUtils.BeNotBlank(jsonStr)){
            List<DynamicModel> dynamicModels=toList(jsonStr,DynamicModel.class);
            if(CommonUtils.BeNotEmpty(dynamicModels)){
                Collections.sort(dynamicModels);
            }
            return dynamicModels;
        }
        return new ArrayList<>();
    }

    public static List<DynamicModel> listExtendModel(String jsonStr) {
        List<DynamicModel>  extendFields= jsonStrToModel(jsonStr);
        if(CommonUtils.BeNotEmpty(extendFields)){
            return  extendFields.stream()
                    .filter(DynamicModel::getIsExtend)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static String modelToJsonStr(List<DynamicModel> dynamicModel) {
        if(CommonUtils.BeNotEmpty(dynamicModel)){
            return toJsonStr(dynamicModel);
        }
        return null;
    }

    public static List<DynamicModel> listDefaultModel(String jsonStr) {
        List<DynamicModel>  defaultFields= jsonStrToModel(jsonStr);
        if(CommonUtils.BeNotEmpty(defaultFields)){
            return  defaultFields.stream()
                    .filter(field -> !field.getIsExtend())
                    .collect(Collectors.toList());

        }
        return new ArrayList<>();
    }

    public static void setFieldVal(List<DynamicModel> modelFields, BaseEntity baseDto, boolean setField) {
        if(CommonUtils.BeNotEmpty(modelFields)){
            BeanPropertyBox propertyBox=new BeanPropertyBox(baseDto);
            Iterator iterator = propertyBox.iterator(BeanPropertyIterator.class);
            while(iterator.hasNext()){
                Field field = (Field)iterator.next();
                if(CommonUtils.BeNotNull(field)){
                    for(DynamicModel modelField:modelFields){
                        if(field.getName().equals(modelField.getFieldCode())){
                            try {
                                if(setField){
                                    modelField.setDefaultValue(field.get(baseDto));
                                }else{
                                    field.set(baseDto,modelField.getDefaultValue());
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

    }
    public static void setFieldVal(BaseField baseDto, Map map){
        if(CommonUtils.BeNotNull(baseDto) && CommonUtils.BeNotNull(map)){
            List<Field> fields = Arrays.asList(baseDto.getClass().getDeclaredFields());
            if(CommonUtils.BeNotEmpty(fields)){
                for( Field field:fields){
                    field.setAccessible(true);
                    boolean hasValue = map.containsKey(field.getName()) && CommonUtils.BeNotNull(map.get(field.getName()));
                    if(hasValue){
                        try {
                            field.set(baseDto,map.get(field.getName()));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    public static void filterMapToSetFieldValue(List<DynamicModel> dynamicModels, Map<String, Object> params) {
        if(CommonUtils.BeNotEmpty(dynamicModels)  && params!=null  && !params.isEmpty()){
            for(DynamicModel dynamicModel:dynamicModels){
                String code = dynamicModel.getFieldCode();
                if(params.containsKey(code)){
                    dynamicModel.setDefaultValue(params.get(code));
                }
            }
        }
    }

    public static void copyField(List<DynamicModel> dest, List<DynamicModel> src) {
        if(CommonUtils.BeNotEmpty(src)){
            for(DynamicModel d:dest){
                for(DynamicModel s:src){
                    if(d.getFieldCode().equals(s.getFieldCode()) && d.getInputType().equals(s.getInputType())){
                        d.setDefaultValue(s.getDefaultValue());
                        continue;
                    }
                }
            }
        }
    }

    public static void formatData(Map<String, Object> map) {
        if(map!=null && !map.isEmpty()){
            if(map.containsKey(DATA) && CommonUtils.BeNotNull(map.get(DATA))){
                List<DynamicModel> dynamicModels=jsonStrToModel(map.get(DATA).toString());
                replaceMap(dynamicModels,map);
            }
        }
    }

    private static void replaceMap(List<DynamicModel> dynamicModels, Map<String, Object> map) {
        boolean isReplace = CommonUtils.BeNotEmpty(dynamicModels) && map!=null && !map.isEmpty();
        if(isReplace){
            for(DynamicModel dynamicModel:dynamicModels){
                String inputType = dynamicModel.getInputType();
                Object defaultValue= dynamicModel.getDefaultValue();
                String fieldCode = dynamicModel.getFieldCode();
                boolean isPicture = InputTypeEnum.INPUT_PICTURE.getCode().equals(inputType);
                boolean isTag = InputTypeEnum.INPUT_TAG.getCode().equals(inputType);
                if(isPicture){
                    if(CommonUtils.BeNotNull(defaultValue) && CommonUtils.BeNotBlank(defaultValue.toString())){
                        List<Map> covers = toList(defaultValue.toString(),Map.class);
                        map.put(fieldCode,CommonUtils.BeNotEmpty(covers)?covers.get(0):null);
                    }
                }else if(isTag ){
                    if(CommonUtils.BeNotNull(defaultValue) && CommonUtils.BeNotBlank(defaultValue.toString())){
                        List<String> tags = Arrays.asList(defaultValue.toString().split(","));
                        map.put(fieldCode,tags);
                    }
                }else{
                    map.put(fieldCode,defaultValue);
                }

            }
        }
    }

    public static void formatCover(Map<String, Object> map, boolean getFrist) {
        if(map!=null && !map.isEmpty()){
            if(map.containsKey(COVER) && CommonUtils.BeNotNull(map.get(COVER))){
                List<Map> covers = toList(map.get(COVER).toString(),Map.class);
                if(getFrist){
                    map.put(COVER,CommonUtils.BeNotEmpty(covers)?covers.get(0):null);
                }else{
                    map.put(COVER,covers);
                }
            }
        }
    }

    public static void formatDatas(List<Map<String, Object>> maps) {
        if(CommonUtils.BeNotEmpty(maps)){
            for(Map map:maps){
                formatData(map);
            }
        }
    }

    public static void formatCoverObjs(List<Map<String, Object>> contents, boolean b) {
        if(CommonUtils.BeNotEmpty(contents)){
            for(Map map:contents){
                formatCover(map,b);
            }
        }
    }
}
