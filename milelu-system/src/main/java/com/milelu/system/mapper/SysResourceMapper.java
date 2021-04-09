package com.milelu.system.mapper;

import java.util.List;
import com.milelu.system.domain.SysResource;

/**
 * 资源Mapper接口
 *
 * @author MILELU
 * @date 2021-01-13
 */
public interface SysResourceMapper
{
    /**
     * 查询资源
     *
     * @param id 资源ID
     * @return 资源
     */
    public SysResource selectSysResourceById(String id);

    /**
     * 查询资源列表
     *
     * @param sysResource 资源
     * @return 资源集合
     */
    public List<SysResource> selectSysResourceList(SysResource sysResource);

    /**
     * 新增资源
     *
     * @param sysResource 资源
     * @return 结果
     */
    public int insertSysResource(SysResource sysResource);

    /**
     * 修改资源
     *
     * @param sysResource 资源
     * @return 结果
     */
    public int updateSysResource(SysResource sysResource);

    /**
     * 删除资源
     *
     * @param id 资源ID
     * @return 结果
     */
    public int deleteSysResourceById(String id);

    /**
     * 批量删除资源
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysResourceByIds(String[] ids);
}
