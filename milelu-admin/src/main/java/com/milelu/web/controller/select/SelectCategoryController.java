package com.milelu.web.controller.select;

import java.util.List;

import com.milelu.service.service.select.SelectCategoryService;
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
import com.milelu.service.domain.SelectCategory;
import com.milelu.common.utils.poi.ExcelUtil;
import com.milelu.common.core.page.TableDataInfo;

/**
 * 选版-分类Controller
 *
 * @author MILELU
 * @date 2021-04-09
 */
@RestController
@RequestMapping("/select/category")
public class SelectCategoryController extends BaseController
{
    @Autowired
    private SelectCategoryService selectCategoryService;

    /**
     * 查询选版-分类列表
     */
    @PreAuthorize("@ss.hasPermi('select:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(SelectCategory selectCategory)
    {
        startPage();
        List<SelectCategory> list = selectCategoryService.selectSelectCategoryList(selectCategory);
        return getDataTable(list);
    }

    /**
     * 导出选版-分类列表
     */
    @PreAuthorize("@ss.hasPermi('select:category:export')")
    @Log(title = "选版-分类", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SelectCategory selectCategory)
    {
        List<SelectCategory> list = selectCategoryService.selectSelectCategoryList(selectCategory);
        ExcelUtil<SelectCategory> util = new ExcelUtil<SelectCategory>(SelectCategory.class);
        return util.exportExcel(list, "category");
    }

    /**
     * 获取选版-分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('select:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(selectCategoryService.selectSelectCategoryById(id));
    }

    /**
     * 新增选版-分类
     */
    @PreAuthorize("@ss.hasPermi('select:category:add')")
    @Log(title = "选版-分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SelectCategory selectCategory)
    {
        return toAjax(selectCategoryService.insertSelectCategory(selectCategory));
    }

    /**
     * 修改选版-分类
     */
    @PreAuthorize("@ss.hasPermi('select:category:edit')")
    @Log(title = "选版-分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SelectCategory selectCategory)
    {
        return toAjax(selectCategoryService.updateSelectCategory(selectCategory));
    }

    /**
     * 删除选版-分类
     */
    @PreAuthorize("@ss.hasPermi('select:category:remove')")
    @Log(title = "选版-分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(selectCategoryService.deleteSelectCategoryByIds(ids));
    }
}
