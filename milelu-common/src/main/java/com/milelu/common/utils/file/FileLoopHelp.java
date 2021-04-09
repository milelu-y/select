package com.milelu.common.utils.file;

import com.milelu.common.core.domain.TreeFileModel;

public abstract class FileLoopHelp {

    /**
     * 过滤文件属性并操作
     * @param treeFileModel
     */
     protected abstract void filter(TreeFileModel treeFileModel);

    /**
     * 是否设置查询时禁选择 文件夹
     * @return
     */
     protected abstract Boolean setDisableFolder();

    /**
     * 是否加载页面片段
     * @return
     */
     protected abstract Boolean loadFragment();


    /**
     * 是否只加载页面片段
     * @return
     */
     protected abstract Boolean justLoadFragment();

    /**
     * 是否过滤
     * @return
     */
     protected abstract Boolean startFilter(TreeFileModel treeFileModel);
}