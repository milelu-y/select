package com.milelu.service.mapper;

import java.util.List;
import java.util.Map;

import com.milelu.service.domain.Site;

/**
 * 站点Mapper接口
 *
 * @author MILELU
 * @date 2020-12-14
 */
public interface SiteMapper
{
    /**
     * 查询站点
     *
     * @param id 站点ID
     * @return 站点
     */
    public Site selectSiteById(Integer id);

    /**
     * 查询站点列表
     *
     * @param site 站点
     * @return 站点集合
     */
    public List<Site> selectSiteList(Site site);

    /**
     * 新增站点
     *
     * @param site 站点
     * @return 结果
     */
    public int insertSite(Site site);

    /**
     * 修改站点
     *
     * @param site 站点
     * @return 结果
     */
    public int updateSite(Site site);

    /**
     * 删除站点
     *
     * @param id 站点ID
     * @return 结果
     */
    public int deleteSiteById(Integer id);

    /**
     * 批量删除站点
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSiteByIds(Integer[] ids);

    /**
     * 获取站点信息
     * @return
     */
    Map<String, Object> getSiteInfo();

}
