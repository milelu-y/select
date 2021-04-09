package com.milelu.web.controller.category;

import java.util.List;

import com.milelu.common.annotation.ResponseResult;
import com.milelu.common.utils.model.ValidList;
import com.milelu.service.service.category.CategoryModelRelationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
import com.milelu.service.domain.CategoryModelRelation;
import com.milelu.common.utils.poi.ExcelUtil;
import com.milelu.common.core.page.TableDataInfo;

/**
 * 分类选择模型时关系 该模型值 tk_model  不是 分类模型Controller
 *
 * @author MILELU
 * @date 2021-01-22
 */
@Validated
@RestController
@RequestMapping("/model/relation")
public class CategoryModelRelationController extends BaseController
{
    @Autowired
    private CategoryModelRelationService categoryModelRelationService;

    /**
     * 查询分类选择模型时关系分类模型列表
     */
    @PreAuthorize("@ss.hasPermi('model:relation:list')")
    @GetMapping("/list")
    public TableDataInfo list(CategoryModelRelation categoryModelRelation)
    {
        startPage();
        List<CategoryModelRelation> list = categoryModelRelationService.selectCategoryModelRelationList(categoryModelRelation);
        return getDataTable(list);
    }

    /**
     * 导出分类选择模型时关系
     */
    @PreAuthorize("@ss.hasPermi('model:relation:export')")
    @Log(title = "分类选择模型时关系 该模型值 tk_model  不是 分类模型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CategoryModelRelation categoryModelRelation)
    {
        List<CategoryModelRelation> list = categoryModelRelationService.selectCategoryModelRelationList(categoryModelRelation);
        ExcelUtil<CategoryModelRelation> util = new ExcelUtil<CategoryModelRelation>(CategoryModelRelation.class);
        return util.exportExcel(list, "relation");
    }

    /**
     * 分类模型详细信息
     */
    @PreAuthorize("@ss.hasPermi('model:relation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(categoryModelRelationService.selectCategoryModelRelationById(id));
    }

    /**
     * 新增分类选择模型时关系 该模型值 tk_model  不是 分类模型
     */
    @PreAuthorize("@ss.hasPermi('model:relation:add')")
    @Log(title = "添加分类选择模型时关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CategoryModelRelation categoryModelRelation)
    {
        return toAjax(categoryModelRelationService.insertCategoryModelRelation(categoryModelRelation));
    }

    /**
     * 修改分类选择模型时关系 该模型值 tk_model  不是 分类模型
     */
    @PreAuthorize("@ss.hasPermi('model:relation:edit')")
    @Log(title = "修改分类选择模型时关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CategoryModelRelation categoryModelRelation)
    {
        return toAjax(categoryModelRelationService.updateCategoryModelRelation(categoryModelRelation));
    }

    /**
     * 删除分类选择模型时关系 该模型值 tk_model  不是 分类模型
     */
    @PreAuthorize("@ss.hasPermi('model:relation:remove')")
    @Log(title = "删除分类选择模型时关系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(categoryModelRelationService.deleteCategoryModelRelationByIds(ids));
    }


    @PostMapping("/saveBatch")
    @ResponseResult(isAjaxResult = true)
    public void saveBatch(@Validated @RequestBody ValidList<CategoryModelRelation> vs){
        categoryModelRelationService.saveBatch(vs);
    }


}
