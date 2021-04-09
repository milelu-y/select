package com.milelu.service.service.file;

import java.util.List;
import java.util.Map;

import com.milelu.common.core.domain.AjaxResult;
import com.milelu.service.domain.FragmentAttribute;

/**
 * 页面片段数据Service接口
 *
 * @author MILELU
 * @date 2021-01-11
 */
public interface FragmentAttributeService
{
    /**
     * 查询页面片段数据
     *
     * @param id 页面片段数据ID
     * @return 页面片段数据
     */
    public FragmentAttribute selectFragmentAttributeById(String id);

    /**
     * 查询页面片段数据列表
     *
     * @param fragmentAttribute 页面片段数据
     * @return 页面片段数据集合
     */
    public List<FragmentAttribute> selectFragmentAttributeList(FragmentAttribute fragmentAttribute);

    /**
     * 新增页面片段数据
     *
     * @param fragmentAttribute 页面片段数据
     * @return 结果
     */
    public void insertFragmentAttribute(FragmentAttribute fragmentAttribute);

    /**
     * 修改页面片段数据
     *
     * @param fragmentAttribute 页面片段数据
     * @return 结果
     */
    public void updateFragmentAttribute(FragmentAttribute fragmentAttribute);

    /**
     * 批量删除页面片段数据
     *
     * @param ids 需要删除的页面片段数据ID
     * @return 结果
     */
    public int deleteFragmentAttributeByIds(String[] ids);

    /**
     * 删除页面片段数据信息
     *
     * @param id 页面片段数据ID
     * @return 结果
     */
    public int deleteFragmentAttributeById(String id);

    /**
     * 获取表格编辑
     * @param id
     * @return
     */
    Map<String, Object> getDesignAttrById(String id);

    List<Map> listDataByCode(String code);
}
