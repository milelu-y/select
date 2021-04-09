package com.milelu.service.service.file.impl;

import com.milelu.common.config.MileluConfig;
import com.milelu.common.constant.Channel;
import com.milelu.common.constant.Constants;
import com.milelu.common.core.component.template.MetadataComponent;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.core.domain.FileInfo;
import com.milelu.common.core.domain.Metadata;
import com.milelu.common.core.domain.TreeFileInfo;
import com.milelu.common.exception.CustomException;
import com.milelu.common.utils.CommonUtils;
import com.milelu.common.utils.file.FileUtils;
import com.milelu.freemark.component.TemplateComponent;
import com.milelu.freemark.emums.ChannelEnum;
import com.milelu.freemark.handler.DirectiveInterceptor;
import com.milelu.freemark.processor.message.MessageSend;
import com.milelu.service.service.file.TemplateFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MILELU
 * @date 2020/12/17 10:03
 */
@Slf4j
@Service
public class TemplateFileServiceImpl implements TemplateFileService {

    @Autowired
    private MetadataComponent metadataComponent;

    @Autowired
    MessageSend messageSend;

    @Autowired
    protected DirectiveInterceptor interceptor;

    @Autowired
    private TemplateComponent templateComponent;

    public static final String CONTENT="<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Title</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    @Override
    public TreeFileInfo TemplateTree() {
        TreeFileInfo treeFileInfo = new TreeFileInfo();

        recursionTree(MileluConfig.getWebTemplateFilePath(), treeFileInfo);

        return treeFileInfo;
    }

    private TreeFileInfo recursionTree(String path, TreeFileInfo treeFileInfoDto) {
        List<TreeFileInfo> treefiles = buildData(FileUtils.getFileList(path, null));
        if (CommonUtils.isNotEmptyList(treefiles)) {
            treeFileInfoDto.setChildren(treefiles);
            for (TreeFileInfo treeFileInfo : treeFileInfoDto.getChildren()) {
                //TODO:可以拿到别名渲染
                String relativePath = path.replace(MileluConfig.getWebTemplateFilePath(), Constants.BLANK) + File.separator + treeFileInfo.getFileInfo().getFileName();
                treeFileInfo.setRelativePath(relativePath);
                if (treeFileInfo.getFileInfo().isDirectory()) {
                    String filePath = path + File.separator + treeFileInfo.getFileInfo().getFileName();
                    recursionTree(filePath, treeFileInfo);
                }
            }
        }
        return treeFileInfoDto;
    }

    private List<TreeFileInfo> buildData(List<FileInfo> fileList) {
        List<TreeFileInfo> treeFileInfos = new ArrayList<>();
        if (CommonUtils.isNotEmptyList(fileList)) {
            fileList.forEach(fileInfo -> {
                TreeFileInfo treeFileInfo = new TreeFileInfo();
                treeFileInfo.setFileInfo(fileInfo);
                treeFileInfo.setIsLeaf(!fileInfo.isDirectory());
                Integer key = fileInfo.hashCode();
                treeFileInfo.setTitle(fileInfo.getFileName()).setKey(String.valueOf(key));
                treeFileInfo.setLabel(fileInfo.getFileName());
                treeFileInfos.add(treeFileInfo);
            });
        }
        return treeFileInfos;
    }

    @Override
    public AjaxResult getContent(String path) {
        String filePath = MileluConfig.getWebTemplateFilePath() + File.separator + path;
        File file = new File(filePath);
        try {
            if (file.isFile()) {
                String fileContent = org.apache.commons.io.FileUtils.readFileToString(file, Constants.UTF8);
                return AjaxResult.success(fileContent);
            }
        } catch (IOException e) {
            log.error("读取模板文件失败！");
            return null;
        }
        return null;
    }

    @Override
    public void updateTemplateContent(String path, String content) {
        String filePath = MileluConfig.getWebTemplateFilePath() + File.separator + path;
        File file = new File(filePath);
        if (!file.exists()) {
            throw new CustomException("文件不存在!");
        }
        try {
            content = URLDecoder.decode(content, Constants.UTF8);
            org.apache.commons.io.FileUtils.writeStringToFile(file, content, Constants.UTF8);
        } catch (IOException e) {
            log.error("写入模板文件失败！");
            e.printStackTrace();
        }
    }

    @Override
    public AjaxResult deleteTemplate(String path) {
        String filePath = MileluConfig.getWebTemplateFilePath() + File.separator + path;
        metadataComponent.deleteTemplateMetadata(filePath);
        File file = new File(filePath);
        boolean res = true;
        if (file.exists()) {
            if (file.isFile()) {
                res = file.delete();
            } else {
                //文件夹递归删除
                recursionDeleteFile(file);
            }
        }
        if (res) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error("文件不存在!");
        }
    }

    private void recursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    recursionDeleteFile(f);
                } else {
                    f.delete();
                }
            }
            file.delete();
        }
    }

    @Override
    public void addTemplate(Metadata metadata) {
        if (CommonUtils.isNotEmptyObject(metadata)) {

            //TODO:此处可以给默认路径
            if (CommonUtils.isNotEmptyString(metadata.getPath())) {
                String filePath = MileluConfig.getWebTemplateFilePath() + File.separator + metadata.getPath();
                try {
                    //创建模板
                    FileUtils.createFile(filePath, CONTENT);
                    //TODO:构造元数据到数据库中
                    metadataComponent.updateTemplateMetadata(filePath, metadata);
                    //TODO:发布静态文件
                    if (CommonUtils.BeNotBlank(metadata.getPublishPath())){
                        publish(metadata.getPublishPath());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Metadata getSingleTemplateMetadataByPath(String path) throws FileNotFoundException {
        String filePath = MileluConfig.getWebTemplateFilePath() + File.separator + path;
        return metadataComponent.getTemplateMetadata(filePath);
    }

    @Override
    public void updateTemplateMetadata(Metadata metadata) {
        if (CommonUtils.isNotEmptyObject(metadata)) {
            //TODO:此处可以给默认路径
            if (CommonUtils.isNotEmptyString(metadata.getPath())) {
                String filePath = MileluConfig.getWebTemplateFilePath() + File.separator + metadata.getPath();
                    //TODO:构造元数据
                    metadataComponent.updateTemplateMetadata(filePath, metadata);
                    //TODO:发布静态文件
            }
        }
    }

    @Override
    public void generateTemplate(Map<String, Object> map) {
        Metadata metadata = null;
        try {
            metadata = getSingleTemplateMetadataByPath((String) map.get("relativePath"));
        } catch (FileNotFoundException e) {
            throw new CustomException("系统找不到指定的文件");
        }
        if (CommonUtils.BeNotNull(metadata)){
            if (CommonUtils.BeNotBlank(metadata.getPublishPath())&&!"".equals(metadata.getPublishPath())){
                map.put("metadata",metadata);
                messageSend.sendMessage(ChannelEnum.TEMPLATE, Channel.NOTIFY_IT, map, true);
            }else {
               throw  new CustomException("发布路径不能为空!");
            }
        }else {
            throw  new CustomException("请填写模板元数据!");
        }
    }

    /**
     * 发布静态文件
     */
    private void publish(String path){
        templateComponent.createStaticFile(path,null,null,null);
    }
}
