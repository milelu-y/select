package com.milelu.system.service.impl;

import java.util.List;
import com.milelu.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.system.mapper.SysResourceMapper;
import com.milelu.system.domain.SysResource;
import com.milelu.system.service.SysResourceService;

/**
 * 资源Service业务层处理
 *
 * @author MILELU
 * @date 2021-01-13
 */
@Service
public class SysResourceServiceImpl implements SysResourceService
{
    @Autowired
    private SysResourceMapper sysResourceMapper;

    /**
     * 查询资源
     *
     * @param id 资源ID
     * @return 资源
     */
    @Override
    public SysResource selectSysResourceById(String id)
    {
        return sysResourceMapper.selectSysResourceById(id);
    }

    /**
     * 查询资源列表
     *
     * @param sysResource 资源
     * @return 资源
     */
    @Override
    public List<SysResource> selectSysResourceList(SysResource sysResource)
    {
        return sysResourceMapper.selectSysResourceList(sysResource);
    }

    /**
     * 新增资源
     *
     * @param sysResource 资源
     * @return 结果
     */
    @Override
    public int insertSysResource(SysResource sysResource)
    {
        sysResource.setCreateTime(DateUtils.getNowDate());
        return sysResourceMapper.insertSysResource(sysResource);
    }

    /**
     * 修改资源
     *
     * @param sysResource 资源
     * @return 结果
     */
    @Override
    public int updateSysResource(SysResource sysResource)
    {
        sysResource.setUpdateTime(DateUtils.getNowDate());
        return sysResourceMapper.updateSysResource(sysResource);
    }

    /**
     * 批量删除资源
     *
     * @param ids 需要删除的资源ID
     * @return 结果
     */
    @Override
    public int deleteSysResourceByIds(String[] ids)
    {
        return sysResourceMapper.deleteSysResourceByIds(ids);
    }

    /**
     * 删除资源信息
     *
     * @param id 资源ID
     * @return 结果
     */
    @Override
    public int deleteSysResourceById(String id)
    {
        return sysResourceMapper.deleteSysResourceById(id);
    }
}
