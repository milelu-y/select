package com.milelu.common.utils.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author MILELU
 */
@Data
@Accessors(chain = true)
public class KeyValueModel implements Serializable {

    public KeyValueModel(){}


    public KeyValueModel(String code,String value){
        this.code = code;
        this.value =value;
    }


    private String code;

    private String value;
}
