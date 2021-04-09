package com.milelu.web.controller.category;

import java.util.List;
import java.util.Map;

import com.milelu.common.annotation.ResponseResult;
import com.milelu.common.utils.model.KeyValueModel;
import com.milelu.common.utils.model.Tree;
import com.milelu.service.domain.CategoryAttribute;
import com.milelu.service.service.category.CategoryAttributeService;
import com.milelu.service.service.category.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.milelu.common.annotation.Log;
import com.milelu.common.core.controller.BaseController;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.enums.BusinessType;
import com.milelu.service.domain.Category;
import com.milelu.common.utils.poi.ExcelUtil;
import com.milelu.common.core.page.TableDataInfo;

import javax.validation.constraints.NotBlank;

/**
 * 分类Controller
 *
 * @author MILELU
 * @date 2021-01-18
 */
@RestController
@RequestMapping("/category/category")
public class CategoryController extends BaseController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryAttributeService attributeService;
    /**
     * 查询分类列表
     */
    @PreAuthorize("@ss.hasPermi('category:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(Category category) {
        startPage();
        List<Category> list = categoryService.selectCategoryList(category);
        return getDataTable(list);
    }

    /**
     * 查询分类树
     */
    @GetMapping("/tree")
    @ResponseResult(isAjaxResult = true)
    public Tree<Category> treeCategory() {
        return categoryService.treeCategory();
    }

    /**
     * 导出分类列表
     */
    @PreAuthorize("@ss.hasPermi('category:category:export')")
    @Log(title = "分类", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Category category) {
        List<Category> list = categoryService.selectCategoryList(category);
        ExcelUtil<Category> util = new ExcelUtil<Category>(Category.class);
        return util.exportExcel(list, "category");
    }

    /**
     * 获取分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('category:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(categoryService.selectCategoryById(id));
    }
    /**
     * 获取分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('category:category:query')")
    @GetMapping(value = "/getDetail/{id}")
    public Map<String,Object> getDetail(@PathVariable("id") String id) {
        return categoryService.getDetail(id);
    }

    /**
     * 新增分类
     */
    @PreAuthorize("@ss.hasPermi('category:category:add')")
    @Log(title = "分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Category category) {
        return categoryService.insertCategory(category);
    }

    /**
     * 修改分类
     */
    @PreAuthorize("@ss.hasPermi('category:category:edit')")
    @Log(title = "分类", businessType = BusinessType.UPDATE)
    @PutMapping
    @ResponseResult(isAjaxResult = true)
    public void edit(@RequestBody Category category) {
       categoryService.updateCategory(category);
    }

    /**
     * 删除分类
     */
    @PreAuthorize("@ss.hasPermi('category:category:remove')")
    @Log(title = "分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult removes(@PathVariable String[] ids) {
        return toAjax(categoryService.deleteCategoryByIds(ids));
    }

    /**
     * 删除分类
     */
    @PreAuthorize("@ss.hasPermi('category:category:remove')")
    @Log(title = "分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/delById/{id}")
    @ResponseResult(isAjaxResult = true)
    public void remove(@PathVariable String id) {
       categoryService.deleteById(id);
    }

    /**
     * 获取路径规则
     * @return
     */
    @GetMapping("/loadPathRule")
    @ResponseResult(isAjaxResult = true)
    public List<KeyValueModel> loadPathRule(){
        return categoryService.loadPathRule();
    }

    /**
     * 获取模板文件
     * @return
     */
    @GetMapping("/loadTemplate")
    @ResponseResult(isAjaxResult = true)
    public Map<String,Object> loadTemplate(){
        return categoryService.loadTemplate();
    }

    /**
     * 根据分类ID获取SEO信息
     */
    @GetMapping("/getSeo")
    @ResponseResult(isAjaxResult = true)
    public CategoryAttribute getSeo(@RequestParam String categoryId){
        return attributeService.getSeo(categoryId);
    }

    /**
     * 根据分类ID修改Attribute
     */
    @PreAuthorize("@ss.hasPermi('category:category:edit')")
    @Log(title = "修改SEO", businessType = BusinessType.UPDATE)
    @PutMapping("/updateCategoryAttribute")
    @ResponseResult(isAjaxResult = true)
    public void updateAttributeSeo(@RequestBody  CategoryAttribute categoryAttribute){
         attributeService.updateAttributeSeo(categoryAttribute);
    }

    @PutMapping("/genCategory")
    @ResponseResult(isAjaxResult = true)
    public void genCategory(@NotBlank @RequestBody Category category){
        categoryService.genCategory(category.getId());
    }

}
