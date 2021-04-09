package com.milelu.service.service.model.impl;

import com.milelu.common.core.domain.TreeFileModel;
import com.milelu.common.utils.file.FileLoopHelp;

/**
 * @author MILELU
 * @date 2021/1/19 14:35
 */
public class ModelLoopHelp extends FileLoopHelp {

    @Override
    protected void filter(TreeFileModel treeFileModel) {

    }

    @Override
    protected Boolean setDisableFolder() {
        return true;
    }

    @Override
    protected Boolean loadFragment() {
        return false;
    }

    @Override
    protected Boolean justLoadFragment() {
        return false;
    }

    @Override
    protected Boolean startFilter(TreeFileModel treeFileModel) {
        return false;
    }
}
