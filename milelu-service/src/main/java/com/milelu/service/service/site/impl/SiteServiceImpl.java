package com.milelu.service.service.site.impl;

import java.util.List;
import java.util.Map;

import com.milelu.service.service.site.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.service.mapper.SiteMapper;
import com.milelu.service.domain.Site;

/**
 * 站点Service业务层处理
 *
 * @author MILELU
 * @date 2020-12-14
 */
@Service
public class SiteServiceImpl implements SiteService
{
    @Autowired
    private SiteMapper siteMapper;

    /**
     * 查询站点
     *
     * @param id 站点ID
     * @return 站点
     */
    @Override
    public Site selectSiteById(Integer id)
    {
        return siteMapper.selectSiteById(id);
    }

    /**
     * 查询站点列表
     *
     * @param site 站点
     * @return 站点
     */
    @Override
    public List<Site> selectSiteList(Site site)
    {
        return siteMapper.selectSiteList(site);
    }

    /**
     * 新增站点
     *
     * @param site 站点
     * @return 结果
     */
    @Override
    public int insertSite(Site site)
    {
        return siteMapper.insertSite(site);
    }

    /**
     * 修改站点
     *
     * @param site 站点
     * @return 结果
     */
    @Override
    public int updateSite(Site site)
    {
        return siteMapper.updateSite(site);
    }

    /**
     * 批量删除站点
     *
     * @param ids 需要删除的站点ID
     * @return 结果
     */
    @Override
    public int deleteSiteByIds(Integer[] ids)
    {
        return siteMapper.deleteSiteByIds(ids);
    }

    /**
     * 删除站点信息
     *
     * @param id 站点ID
     * @return 结果
     */
    @Override
    public int deleteSiteById(Integer id)
    {
        return siteMapper.deleteSiteById(id);
    }

    /**
     * 获取站点信息
     * @return
     */
    @Override
    public Map<String, Object> getSiteInfo() {
        //TODO:暂且不处理动态站点只获取唯一一个
        return siteMapper.getSiteInfo();
    }
}
