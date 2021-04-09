package com.milelu.service.service.file;

import java.util.List;
import java.util.Map;

import com.milelu.common.core.domain.TreeFileInfo;
import com.milelu.common.core.domain.TreeFileModel;
import com.milelu.service.domain.FragmentModel;

/**
 * 页面片段文件模型Service接口
 *
 * @author MILELU
 * @date 2021-01-07
 */
public interface FragmentModelService
{
    /**
     * 查询页面片段文件模型
     *
     * @param id 页面片段文件模型ID
     * @return 页面片段文件模型
     */
    public FragmentModel selectFragmentModelById(String id);

    /**
     * 查询页面片段文件模型列表
     *
     * @param fragmentModel 页面片段文件模型
     * @return 页面片段文件模型集合
     */
    public List<FragmentModel> selectFragmentModelList(FragmentModel fragmentModel);

    /**
     * 新增页面片段文件模型
     *
     * @param fragmentModel 页面片段文件模型
     * @return 结果
     */
    public void insertFragmentModel(FragmentModel fragmentModel);

    /**
     * 修改页面片段文件模型
     *
     * @param fragmentModel 页面片段文件模型
     * @return 结果
     */
    public void updateFragmentModel(FragmentModel fragmentModel);

    /**
     * 批量删除页面片段文件模型
     *
     * @param ids 需要删除的页面片段文件模型ID
     * @return 结果
     */
    public int deleteFragmentModelByIds(String[] ids);

    /**
     * 删除页面片段文件模型信息
     *
     * @param id 页面片段文件模型ID
     * @return 结果
     */
    public int deleteFragmentModelById(String id);

    /**
     * 获取页面片段树
     * @return
     */
    List<TreeFileModel> loadTemplateTree();

    /**
     * 获取页面片段map
     * @return
     */
    Map<String, String> getMap();

    /**
     * 根据路径获取内容
     * @param path
     */
    String getFragmentContent(String path);

    /**
     * 设置片段内容
     * @param path,content
     */
    void saveFragmentContent(String path, String content);

    /**
     * 根据路径删除页面片段
     * @param path
     */
    void delFragment(String path);

    /**
     * 获取页面片段文件模型详细信息
     * @param fileName
     * @return
     */
    FragmentModel selectFragmentModelByFileName(String fileName);

    /**
     * 根据文件名称获取动态表单
     * @param fileName
     * @return
     */
    Map<String, Object> getFragmentForms(String fileName);

    /**
     * 根据id 查询指定字段
     * @param id
     * @param fieldType 0：all_field_list 1：extend_field_list
     * @return
     */
    String getFragmentFieldListJsonStr(String id, int fieldType);

    String getFragmentPathByCode(String code);

}
