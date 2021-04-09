package com.milelu.web.controller.select;

import java.util.List;

import com.milelu.service.service.select.SelectTemplateService;
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
import com.milelu.service.domain.SelectTemplate;
import com.milelu.common.utils.poi.ExcelUtil;
import com.milelu.common.core.page.TableDataInfo;

/**
 * 选版模板Controller
 *
 * @author MILELU
 * @date 2021-04-09
 */
@RestController
@RequestMapping("/select/template")
public class SelectTemplateController extends BaseController
{
    @Autowired
    private SelectTemplateService selectTemplateService;

    /**
     * 查询选版模板列表
     */
    @PreAuthorize("@ss.hasPermi('select:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(SelectTemplate selectTemplate)
    {
        startPage();
        List<SelectTemplate> list = selectTemplateService.selectSelectTemplateList(selectTemplate);
        return getDataTable(list);
    }

    /**
     * 导出选版模板列表
     */
    @PreAuthorize("@ss.hasPermi('select:template:export')")
    @Log(title = "选版模板", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SelectTemplate selectTemplate)
    {
        List<SelectTemplate> list = selectTemplateService.selectSelectTemplateList(selectTemplate);
        ExcelUtil<SelectTemplate> util = new ExcelUtil<SelectTemplate>(SelectTemplate.class);
        return util.exportExcel(list, "template");
    }

    /**
     * 获取选版模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('select:template:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(selectTemplateService.selectSelectTemplateById(id));
    }

    /**
     * 新增选版模板
     */
    @PreAuthorize("@ss.hasPermi('select:template:add')")
    @Log(title = "选版模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SelectTemplate selectTemplate)
    {
        return toAjax(selectTemplateService.insertSelectTemplate(selectTemplate));
    }

    /**
     * 修改选版模板
     */
    @PreAuthorize("@ss.hasPermi('select:template:edit')")
    @Log(title = "选版模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SelectTemplate selectTemplate)
    {
        return toAjax(selectTemplateService.updateSelectTemplate(selectTemplate));
    }

    /**
     * 删除选版模板
     */
    @PreAuthorize("@ss.hasPermi('select:template:remove')")
    @Log(title = "选版模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(selectTemplateService.deleteSelectTemplateByIds(ids));
    }
}
