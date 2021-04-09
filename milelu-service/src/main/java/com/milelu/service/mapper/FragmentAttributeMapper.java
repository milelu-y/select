package com.milelu.service.mapper;

import java.util.List;
import java.util.Map;

import com.milelu.service.domain.FragmentAttribute;
import org.apache.ibatis.annotations.Param;

/**
 * 页面片段数据Mapper接口
 *
 * @author MILELU
 * @date 2021-01-11
 */
public interface FragmentAttributeMapper
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
    public int insertFragmentAttribute(FragmentAttribute fragmentAttribute);

    /**
     * 修改页面片段数据
     *
     * @param fragmentAttribute 页面片段数据
     * @return 结果
     */
    public int updateFragmentAttribute(FragmentAttribute fragmentAttribute);

    /**
     * 删除页面片段数据
     *
     * @param id 页面片段数据ID
     * @return 结果
     */
    public int deleteFragmentAttributeById(String id);

    /**
     * 批量删除页面片段数据
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFragmentAttributeByIds(String[] ids);

    /**
     * 根据模型id获取片段扩展
     * @param id
     * @return
     */
    List<FragmentAttribute> getListFragmentAttributeByModelId(String id);

    List<Map> listDataByCode(@Param("code")String code);
}
