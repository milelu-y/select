package com.milelu.common.utils.iterator;

import com.milelu.common.core.domain.BaseEntity;
import com.milelu.common.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class BeanPropertyIterator implements Iterator {

    public BeanPropertyBox beanPropertyBox;

    private int i=0;


    Map<String,Object> param=new LinkedHashMap<>(16);

    public BeanPropertyIterator(BeanPropertyBox beanPropertyBox){
        this.beanPropertyBox=beanPropertyBox;
    }

    @Override
    public boolean hasNext() {
        return i<beanPropertyBox.size();
    }

    @Override
    public Object next() {
        Object obj=beanPropertyBox.getBean();
        Field field= beanPropertyBox.getField(i);
        field.setAccessible(true);
        try {
            Object value=field.get(obj);
            if(isInvalid(value)){
                i++;
                return null;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        i++;
        return field;
    }

    private boolean isInvalid(Object value){
        if(CommonUtils.BeNull(value)){
            return true;
        }
        if(CommonUtils.BeBlank(value.toString())){
            return true;
        }
        return  false;
    }

    private boolean isIgnoreField(Field field){
//        TableField tableField=field.getAnnotation(TableField.class);
//        if(CommonUtils.BeNotNull(tableField)){
//            if(!tableField.exist()){
//                return true;
//            }
//        }else{
//            return true;
//        }
//        return false;
        return true;
    }
}
