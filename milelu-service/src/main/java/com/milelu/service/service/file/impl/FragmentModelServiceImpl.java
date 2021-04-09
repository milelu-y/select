package com.milelu.service.service.file.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.milelu.common.config.MileluConfig;
import com.milelu.common.constant.Constants;
import com.milelu.common.core.domain.TreeFileInfo;
import com.milelu.common.core.domain.TreeFileModel;
import com.milelu.common.enums.FilterField;
import com.milelu.common.exception.CustomException;
import com.milelu.common.utils.CommonUtils;
import com.milelu.common.utils.Md5;
import com.milelu.common.utils.ModelFieldUtil;
import com.milelu.common.utils.SecurityUtils;
import com.milelu.common.utils.file.FileLoopUtil;
import com.milelu.common.utils.file.FileUtils;
import com.milelu.common.utils.model.DynamicModel;
import com.milelu.common.utils.uuid.SnowflakeIdWorker;
import com.milelu.service.service.file.FragmentModelService;
import com.milelu.service.service.file.TempLoopHelp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.service.mapper.FragmentModelMapper;
import com.milelu.service.domain.FragmentModel;

import static cn.hutool.core.io.FileUtil.isDirectory;
import static cn.hutool.core.io.FileUtil.mainName;

/**
 * 页面片段文件模型Service业务层处理
 *
 * @author MILELU
 * @date 2021-01-07
 */
@Slf4j
@Service
public class FragmentModelServiceImpl implements FragmentModelService {
    @Autowired
    private FragmentModelMapper fragmentModelMapper;

    /**
     * 查询页面片段文件模型
     *
     * @param id 页面片段文件模型ID
     * @return 页面片段文件模型
     */
    @Override
    public FragmentModel selectFragmentModelById(String id) {
        return fragmentModelMapper.selectFragmentModelById(id);
    }

    /**
     * 查询页面片段文件模型列表
     *
     * @param fragmentModel 页面片段文件模型
     * @return 页面片段文件模型
     */
    @Override
    public List<FragmentModel> selectFragmentModelList(FragmentModel fragmentModel) {
        return fragmentModelMapper.selectFragmentModelList(fragmentModel);
    }

    /**
     * 新增页面片段文件模型
     *
     * @param f 页面片段文件模型
     * @return 结果
     */
    @Override
    public void insertFragmentModel(FragmentModel f) {

        if (!CommonUtils.isNotEmptyString(f.getCode())) {
            throw new CustomException("编码不能为空!");
        }
        if (checkCode(f.getCode())) {
            throw new CustomException("编码已存在!");
        }
        Date date = new Date();
        f.setId(SnowflakeIdWorker.getId());
        String fileName = fileRename(f.getPath(), f.getRelativePath(), f.getId());
        f.setFileName(fileName)
                .setCreateId(SecurityUtils.getLoginUser().getUser().getUserId().toString())
                .setCreateTime(date).setUpdateTime(date);
        fragmentModelMapper.insertFragmentModel(f);
    }


    private String fileRename(String path, String relativePath, String id) {
        String fileName = Constants.DEFAULT_FRAGMENT_PREFIX + id;
        createFragment(path, fileName);
        String finalPath = relativePath + Constants.SEPARATOR + fileName + Constants.DEFAULT_HTML_SUFFIX;
        return finalPath.replace("\\", "/");
    }

    private void createFragment(String path, String fileName) {
        try {
            String realPath = path + File.separator + fileName;

            String filePath = realPath + Constants.DEFAULT_HTML_SUFFIX;

            if (isFile(filePath)) {
                throw new CustomException("文件已经存在");
            }
            FileUtil.newFile(filePath).createNewFile();
        } catch (IOException e) {
            log.error("创建文件失败:" + e.getMessage());
        }
    }

    private boolean isFile(String filePath) {
        boolean isLegal = FileUtil.exist(filePath) && FileUtil.isFile(filePath);
        return isLegal;
    }

    /**
     * 检查编码是否存在
     *
     * @param code
     * @return
     */
    private boolean checkCode(String code) {
        return fragmentModelMapper.checkCode(code) > 0;
    }

    /**
     * 修改页面片段文件模型
     *
     * @param fragmentModel 页面片段文件模型
     * @return 结果
     */
    @Override
    public void updateFragmentModel(FragmentModel fragmentModel) {
        ModelFieldUtil.filter(fragmentModel);
        fragmentModelMapper.updateFragmentModel(fragmentModel);
    }

    /**
     * 批量删除页面片段文件模型
     *
     * @param ids 需要删除的页面片段文件模型ID
     * @return 结果
     */
    @Override
    public int deleteFragmentModelByIds(String[] ids) {
        return fragmentModelMapper.deleteFragmentModelByIds(ids);
    }

    /**
     * 删除页面片段文件模型信息
     *
     * @param id 页面片段文件模型ID
     * @return 结果
     */
    @Override
    public int deleteFragmentModelById(String id) {
        return fragmentModelMapper.deleteFragmentModelById(id);
    }

    @Override
    public List<TreeFileModel> loadTemplateTree() {
        String fragmentPath = MileluConfig.getFragmentPath();
        List<TreeFileModel> files = FileLoopUtil.loopLoadFiles(fragmentPath, fragmentPath, new TempLoopHelp(this));
        List<TreeFileModel> root = new ArrayList<>();
        TreeFileModel treeFileModel = new TreeFileModel();
        String md5Key = Md5.md5(fragmentPath);
        treeFileModel.setChildren(files).setKey(md5Key).setValue(md5Key).
                setRelativePath(fragmentPath.replace(MileluConfig.getWebTemplateFilePath(), "")).setIsLeaf(false).
                setPath(fragmentPath).setDisabled(true).setTitle("根目录").setLabel("根目录");
        root.add(treeFileModel);
        return root;
    }

    @Override
    public Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>(16);
        //获取所有页面片段
        List<FragmentModel> fragmentModels = fragmentModelMapper.selectFragmentList();
        if (CommonUtils.isNotEmptyList(fragmentModels)) {
            fragmentModels.forEach(fragmentModel -> {
                //把片段Id 和别名  put到map中返回
                map.put(fragmentModel.getId(), fragmentModel.getAlias());
            });
        }
        return map;
    }

    /**
     * 根据路径获取内容
     */
    @Override
    public String getFragmentContent(String path) {
        if (isFile(path)) {
            return FileUtil.readString(new File(path), Constants.UTF8);
        }
        return null;
    }

    /**
     * 设置片段内容
     *
     * @param path,content
     */
    @Override
    public void saveFragmentContent(String path, String content) {
        if (isFile(path)) {
            content = CommonUtils.isNotEmptyString(content) ? content : "";
            FileUtil.writeString(content, new File(path), Constants.UTF8);
        }
    }


    /**
     * 根据路径删除页面片段
     *
     * @param path
     */
    @Override
    public void delFragment(String path) {
        if (isFile(path) || isDirectory(path)) {
            //删除页面片段
            delFragment(new File(path));
            FileUtil.del(path);
        }
    }

    private void delFragment(File file) {
        if (file.getName().startsWith(Constants.DEFAULT_FRAGMENT_PREFIX)) {
            this.deleteFile(getFragmentId(file.getName()));
        }
    }

    private String getFragmentId(String fileName) {
        if (CommonUtils.isNotEmptyString(fileName)) {
            String id = fileName.replace(Constants.DEFAULT_FRAGMENT_PREFIX, "").
                    replace(Constants.DEFAULT_HTML_SUFFIX, "");
            return id;
        }
        return "";
    }

    private void deleteFile(String id) {
        if (CommonUtils.isNotEmptyString(id)) {
            fragmentModelMapper.deleteFragmentModelById(id);
        }
    }

    /**
     * 获取页面片段文件模型详细信息
     *
     * @param fileName
     * @return
     */
    @Override
    public FragmentModel selectFragmentModelByFileName(String fileName) {
        String id = getId(fileName);
        if (CommonUtils.BeNotBlank(id)) {
            FragmentModel fragmentModel = fragmentModelMapper.selectFragmentModelById(id);
            if (CommonUtils.BeNotNull(fragmentModel)) {
                if (CommonUtils.BeBlank(fragmentModel.getDefaultFieldList())) {
                    fragmentModel.setDefaultFieldList(JSONUtil.toJsonStr(ModelFieldUtil.loadModel(FilterField.FRAGMENT)));
                }
            }
            return fragmentModel;
        }
        return null;
    }

    private String getId(String fileName) {
        if (CommonUtils.isNotEmptyString(fileName)) {
            String id = fileName.replace(Constants.DEFAULT_FRAGMENT_PREFIX, "").
                    replace(Constants.DEFAULT_HTML_SUFFIX, "");
            return id;
        }
        return "";
    }

    /**
     * 根据文件名称获取动态表单
     *
     * @param fileName
     * @return
     */
    @Override
    public Map<String, Object> getFragmentForms(String fileName) {
        Map<String, Object> result = new HashMap<>();
        List<DynamicModel> models = getFragmentDesign(fileName);
        String id = getId(fileName);
        FragmentModel fragmentModel = fragmentModelMapper.selectFragmentModelById(id);
        result.put("models", models);
        result.put("data", fragmentModel);
        return result;
    }

    private List<DynamicModel> getFragmentDesign(String fileName) {
        String id = getId(fileName);
        if (CommonUtils.BeNotBlank(id)) {
            String designStr = fragmentModelMapper.getFragmentDesign(id);
            if (CommonUtils.BeNotBlank(designStr)) {
                return ModelFieldUtil.jsonStrToModel(designStr);
            }
        }
        return new ArrayList<>();
    }

    /**
     * 根据id 查询指定字段
     *
     * @param id
     * @param fieldType 0：all_field_list 1：extend_field_list
     * @return
     */
    @Override
    public String getFragmentFieldListJsonStr(String id, int fieldType) {
        if (fieldType == 0) {
            return fragmentModelMapper.getFragmentDesign(id);
        } else if (fieldType == 1) {
            return fragmentModelMapper.getFragmentExtendDesign(id);
        }
        return null;
    }

    @Override
    public String getFragmentPathByCode(String code) {
        return fragmentModelMapper.getFragmentPathByCode(code);
    }

}
