package com.milelu.common.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain =true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeFileModel {

    public TreeFileModel(){

    }

    public TreeFileModel(String key,String title){
      this.key = key;
      this.title = title;
    }


    private String key;

    private String value;

    private String title;

    private String label;

    private String path;

    private String relativePath;

    private Boolean isLeaf ;

    private FileInfoModel fileInfo;

    private Boolean disabled;// 等同于 isLeaf 如果是文件夹禁用，根据情况处理

    private Boolean isFragment; //是否是页面片段

    private List<TreeFileModel> children = new ArrayList<>();

    @JsonIgnore
    private File file;

}
