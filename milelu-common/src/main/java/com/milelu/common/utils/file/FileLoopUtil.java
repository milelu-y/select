package com.milelu.common.utils.file;
import cn.hutool.core.io.FileUtil;
import com.milelu.common.config.MileluConfig;
import com.milelu.common.core.domain.FileInfoModel;
import com.milelu.common.core.domain.TreeFileModel;
import com.milelu.common.utils.CommonUtils;
import com.milelu.common.utils.Md5;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.util.ArrayList;
import java.util.List;


public class FileLoopUtil {


    public static List<TreeFileModel> loopLoadFiles(String path, String rootPath, FileLoopHelp fileLoopHelp){
        List<TreeFileModel> treeFileModels = new ArrayList<>(16);
        boolean load = CommonUtils.isNotEmptyString(path) && FileUtil.exist(path)  && FileUtil.isDirectory(path);
        if(load){
            File[] files=FileUtil.ls(path);
            loop(files,treeFileModels,rootPath,fileLoopHelp);
            return treeFileModels;
        }else{
            return treeFileModels;
        }
    }


    private static void loop(File[] files, List<TreeFileModel> treeFileModels, String rootPath, FileLoopHelp fileLoopHelp){
        if(null!=files && files.length>0){
           for(File file:files){
               TreeFileModel treeFileModel= fileToTreeModel(file,rootPath,fileLoopHelp);
               if(CommonUtils.isNotEmptyObject(treeFileModel)){
                   treeFileModels.add(treeFileModel);
                   loopDoBuild(treeFileModel, rootPath,fileLoopHelp);
               }
           }
        }
    }

    private static void loopDoBuild(TreeFileModel treeFileModel,String rootPath,FileLoopHelp fileLoopHelp){
        if(isLoop(treeFileModel.getFile())){
            treeFileModel.setChildren(fileToTreeModels(treeFileModel.getFile().listFiles(),rootPath,fileLoopHelp));
            for(TreeFileModel tc:treeFileModel.getChildren()){
                loopDoBuild(tc,rootPath,fileLoopHelp);
            }
        }
    }


    private static boolean isLoop(File file){
        return CommonUtils.isNotEmptyObject(file) && file.isDirectory() && file.listFiles().length>0;
    }


    private static  List<TreeFileModel> fileToTreeModels(File[] files,String rootPath,FileLoopHelp fileLoopHelp){
        List<TreeFileModel> treeFileModelList =new ArrayList<>();
        for(File file:files){
            TreeFileModel treeFileModel= fileToTreeModel(file,rootPath,fileLoopHelp);
            if(CommonUtils.isNotEmptyObject(treeFileModel)){
                treeFileModelList.add(treeFileModel);
            }
        }
        return treeFileModelList;
    }

    private static TreeFileModel fileToTreeModel(File file,String rootPath,FileLoopHelp fileLoopHelp){
        TreeFileModel treeFileModel =new TreeFileModel();
        boolean isFragment = file.isFile() && file.getName().startsWith("fragment_prefix_");
        String md5Key = Md5.md5(file.getAbsolutePath());
        String relativePath = CommonUtils.isNotEmptyObject(rootPath)?file.getPath().replace(rootPath,""):file.getPath();
        relativePath=relativePath.replace("\\","/");
        treeFileModel.setKey(md5Key).setPath(file.getPath()).setIsLeaf(file.isFile()).setTitle(file.getName()).
        setValue(md5Key).setRelativePath(relativePath.replace(MileluConfig.getWebTemplateFilePath(),"")).setIsFragment(isFragment).setFile(file).setLabel(file.getName());
        try {
            Path path= Paths.get(file.getAbsolutePath());
            BasicFileAttributeView basicview=Files.getFileAttributeView(path, BasicFileAttributeView.class);
            FileInfoModel fileInfoModel =new FileInfoModel(file.getName(),file.isDirectory(),basicview.readAttributes());
            treeFileModel.setFileInfo(fileInfoModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(CommonUtils.isNotEmptyObject(fileLoopHelp)){
            treeFileModel= buildByFileLoopHelp(file,treeFileModel,fileLoopHelp);
        }
        return treeFileModel;
    }

    private static TreeFileModel  buildByFileLoopHelp(File file, TreeFileModel treeFileModel, FileLoopHelp fileLoopHelp){
        if(fileLoopHelp.setDisableFolder()) {
            treeFileModel.setDisabled(file.isDirectory());
        }
        if(fileLoopHelp.startFilter(treeFileModel)){
            fileLoopHelp.filter(treeFileModel);
        }
        // 只加载页面片段
        if(fileLoopHelp.justLoadFragment()){
            if(treeFileModel.getFile().isFile() && !treeFileModel.getIsFragment()){
                return null;
            }
        }
        if(!fileLoopHelp.loadFragment()){// 不加载页面片段
            if(treeFileModel.getIsFragment()){
               return null;
            }
        }
        return treeFileModel;
    }






}
