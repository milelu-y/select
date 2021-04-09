package com.milelu.web.controller.site;

import java.util.List;

import com.milelu.common.utils.poi.ExcelUtil;
import com.milelu.service.domain.Site;
import com.milelu.service.service.site.SiteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.milelu.common.annotation.Log;
import com.milelu.common.core.controller.BaseController;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.enums.BusinessType;
import com.milelu.common.core.page.TableDataInfo;

/**
 * 站点Controller
 * 
 * @author MILELU
 * @date 2020-12-14
 */
@RestController
@RequestMapping("/service/site")
public class SiteController extends BaseController
{
    @Autowired
    private SiteService siteService;

    /**
     * 查询站点列表
     */
    @PreAuthorize("@ss.hasPermi('service:site:list')")
    @GetMapping("/list")
    public TableDataInfo list(Site site)
    {
        startPage();
        List<Site> list = siteService.selectSiteList(site);
        return getDataTable(list);
    }

    /**
     * 导出站点列表
     */
    @PreAuthorize("@ss.hasPermi('service:site:export')")
    @Log(title = "站点", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Site site)
    {
        List<Site> list = siteService.selectSiteList(site);
        ExcelUtil<Site> util = new ExcelUtil<Site>(Site.class);
        return util.exportExcel(list, "site");
    }

    /**
     * 获取站点详细信息
     */
    @PreAuthorize("@ss.hasPermi('service:site:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(siteService.selectSiteById(id));
    }

    /**
     * 新增站点
     */
    @PreAuthorize("@ss.hasPermi('service:site:add')")
    @Log(title = "站点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Site site)
    {
        return toAjax(siteService.insertSite(site));
    }

    /**
     * 修改站点
     */
    @PreAuthorize("@ss.hasPermi('service:site:edit')")
    @Log(title = "站点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Site site)
    {
        return toAjax(siteService.updateSite(site));
    }

    /**
     * 删除站点
     */
    @PreAuthorize("@ss.hasPermi('service:site:remove')")
    @Log(title = "站点", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(siteService.deleteSiteByIds(ids));
    }
}
