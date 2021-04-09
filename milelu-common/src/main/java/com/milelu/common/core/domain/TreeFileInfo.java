package com.milelu.common.core.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author MILELU
 * @date 2020/12/17 10:10
 */
@Data
@Accessors(chain = true)
public class TreeFileInfo {
    private String key;

    private String code;

    private String title;


    private String relativePath;

    private Boolean isLeaf ;

    private FileInfo fileInfo;

    private String label;

    private List<TreeFileInfo> children;
}
