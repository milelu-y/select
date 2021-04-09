package com.milelu.web.controller.category;

import com.milelu.common.annotation.Log;
import com.milelu.common.core.controller.BaseController;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.core.page.TableDataInfo;
import com.milelu.common.enums.BusinessType;
import com.milelu.common.utils.poi.ExcelUtil;
import com.milelu.service.domain.CategoryModel;
import com.milelu.service.service.category.CategoryModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类模型Controller
 *
 * @author MILELU
 * @date 2021-01-19
 */
@RestController
@RequestMapping("/category/model")
public class CategoryModelController extends BaseController
{
    @Autowired
    private CategoryModelService categoryModelService;

    /**
     * 查询分类模型列表
     */
    @PreAuthorize("@ss.hasPermi('category:model:list')")
    @GetMapping("/list")
    public TableDataInfo list(CategoryModel categoryModel)
    {
        startPage();
        List<CategoryModel> list = categoryModelService.selectCategoryModelList(categoryModel);
        return getDataTable(list);
    }

    /**
     * 导出分类模型列表
     */
    @PreAuthorize("@ss.hasPermi('category:model:export')")
    @Log(title = "分类模型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CategoryModel categoryModel)
    {
        List<CategoryModel> list = categoryModelService.selectCategoryModelList(categoryModel);
        ExcelUtil<CategoryModel> util = new ExcelUtil<CategoryModel>(CategoryModel.class);
        return util.exportExcel(list, "model");
    }

    /**
     * 获取分类模型详细信息
     */
    @PreAuthorize("@ss.hasPermi('category:model:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(categoryModelService.selectCategoryModelById(id));
    }

    /**
     * 新增分类模型
     */
    @PreAuthorize("@ss.hasPermi('category:model:add')")
    @Log(title = "分类模型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CategoryModel categoryModel)
    {
        return toAjax(categoryModelService.insertCategoryModel(categoryModel));
    }

    /**
     * 修改分类模型
     */
    @PreAuthorize("@ss.hasPermi('category:model:edit')")
    @Log(title = "分类模型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CategoryModel categoryModel)
    {
        return toAjax(categoryModelService.updateCategoryModel(categoryModel));
    }

    /**
     * 删除分类模型
     */
    @PreAuthorize("@ss.hasPermi('category:model:remove')")
    @Log(title = "分类模型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(categoryModelService.deleteCategoryModelByIds(ids));
    }
}
