package com.milelu.service.service.file;


import com.milelu.common.constant.Constants;
import com.milelu.common.core.domain.TreeFileModel;
import com.milelu.common.utils.CommonUtils;
import com.milelu.common.utils.file.FileLoopHelp;

import java.util.HashMap;
import java.util.Map;

public class TempLoopHelp extends FileLoopHelp {

    private FragmentModelService fragmentModelService;


    private Map<String ,String> map =new HashMap<>();

    public TempLoopHelp(){

    }

    public TempLoopHelp(FragmentModelService fragmentModelService){
      this.fragmentModelService = fragmentModelService;
      map= fragmentModelService.getMap();
    }

    @Override
    protected void filter(TreeFileModel treeFileModel) {
        if(!map.isEmpty()){
             String id = getId(treeFileModel.getTitle());
             id = map.get(id);
            if(CommonUtils.isNotEmptyString(id)){
                treeFileModel.setTitle(id+ Constants.DEFAULT_HTML_SUFFIX);
                treeFileModel.setLabel(id+ Constants.DEFAULT_HTML_SUFFIX);
            }
        }
    }

    @Override
    protected Boolean setDisableFolder() {
        return false;
    }

    @Override
    protected Boolean loadFragment() {
        return true;
    }

    @Override
    protected Boolean justLoadFragment() {
        return false;
    }

    @Override
    protected Boolean startFilter(TreeFileModel treeFileModel) {
        return treeFileModel.getIsFragment();
    }

    private String getId(String fileName){
        if(CommonUtils.isNotEmptyString(fileName)){
            String id =fileName.replace(Constants.DEFAULT_FRAGMENT_PREFIX,"").
                    replace(Constants.DEFAULT_HTML_SUFFIX,"");
            return id;
        }
        return "";
    }
}
