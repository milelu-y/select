package com.milelu.service.mapper;

import java.util.List;
import com.milelu.service.domain.FragmentModel;
import org.apache.ibatis.annotations.Param;

/**
 * 页面片段文件模型Mapper接口
 *
 * @author MILELU
 * @date 2021-01-07
 */
public interface FragmentModelMapper
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
    public int insertFragmentModel(FragmentModel fragmentModel);

    /**
     * 修改页面片段文件模型
     *
     * @param fragmentModel 页面片段文件模型
     * @return 结果
     */
    public int updateFragmentModel(FragmentModel fragmentModel);

    /**
     * 删除页面片段文件模型
     *
     * @param id 页面片段文件模型ID
     * @return 结果
     */
    public int deleteFragmentModelById(String id);

    /**
     * 批量删除页面片段文件模型
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFragmentModelByIds(String[] ids);

    /**
     * 检查code是否存在
     * @param code
     * @return
     */
    public Integer checkCode(String code);

    List<FragmentModel> selectFragmentList();

    /**
     * 获取表单字段
     * @param id
     * @return
     */
    String getFragmentDesign(String id);

    /**
     * 获取扩展字段
     * @param id
     * @return
     */
    String getFragmentExtendDesign(@Param("id")String id);

    String getFragmentPathByCode(@Param("code") String code);
}
