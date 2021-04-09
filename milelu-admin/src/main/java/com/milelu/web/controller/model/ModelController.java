package com.milelu.web.controller.model;

import java.util.List;
import java.util.Map;

import com.milelu.common.annotation.ResponseResult;
import com.milelu.common.enums.FilterField;
import com.milelu.common.utils.ModelFieldUtil;
import com.milelu.common.utils.model.DynamicModel;
import com.milelu.common.utils.model.KeyValueModel;
import com.milelu.service.service.model.ModelService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.milelu.common.annotation.Log;
import com.milelu.common.core.controller.BaseController;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.enums.BusinessType;
import com.milelu.service.domain.Model;
import com.milelu.common.utils.poi.ExcelUtil;
import com.milelu.common.core.page.TableDataInfo;

import javax.validation.constraints.NotBlank;

/**
 * 模型Controller
 *
 * @author MILELU
 * @date 2021-01-21
 */
@Validated
@RestController
@RequestMapping("/model/model")
public class ModelController extends BaseController
{
    @Autowired
    private ModelService modelService;

    /**
     * 查询模型列表
     */
    @PreAuthorize("@ss.hasPermi('model:model:list')")
    @GetMapping("/list")
    public TableDataInfo list(Model model)
    {
        startPage();
        List<Model> list = modelService.selectModelList(model);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('model:model:list')")
    @GetMapping("/lists")
    @ResponseResult(isAjaxResult = true)
    public List<Model> lists(@RequestParam String categoryId)
    {
        List<Model> list = modelService.listModel(categoryId);
        return list;
    }

    /**
     * 导出模型列表
     */
    @PreAuthorize("@ss.hasPermi('model:model:export')")
    @Log(title = "模型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Model model)
    {
        List<Model> list = modelService.selectModelList(model);
        ExcelUtil<Model> util = new ExcelUtil<Model>(Model.class);
        return util.exportExcel(list, "model");
    }

    /**
     * 获取模型详细信息
     */
    @PreAuthorize("@ss.hasPermi('model:model:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(modelService.selectModelById(id));
    }

    /**
     * 新增模型
     */
    @PreAuthorize("@ss.hasPermi('model:model:add')")
    @Log(title = "模型", businessType = BusinessType.INSERT)
    @PostMapping
    @ResponseResult(isAjaxResult = true)
    public void add(@RequestBody Model model)
    {
        modelService.insertModel(model);
    }

    /**
     * 修改模型
     */
    @ResponseResult(isAjaxResult = true)
    @PreAuthorize("@ss.hasPermi('model:model:edit')")
    @Log(title = "模型", businessType = BusinessType.UPDATE)
    @PutMapping
    public void edit(@RequestBody Model model)
    {
        modelService.updateModel(model);
    }

    /**
     * 删除模型
     */
    @PreAuthorize("@ss.hasPermi('model:model:remove')")
    @Log(title = "模型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(modelService.deleteModelByIds(ids));
    }

    @GetMapping("/loadTemplate")
    public Map<String,Object> loadTemplateTree(){
        return modelService.loadTemplate();
    }
    @GetMapping("/loadModel")
    public List<DynamicModel> loadModel(){
        return ModelFieldUtil.loadModel(FilterField.MODEL);
    }

    /**
     * 获取模型详细信息
     */
    @PreAuthorize("@ss.hasPermi('model:model:query')")
    @GetMapping(value = "/getDetail/{id}")
    public Model getDetail(@PathVariable("id") String id)
    {
        return modelService.getDetail(id);
    }

    @GetMapping("/listPublishModel")
    @ResponseResult(isAjaxResult = true)
    public List<KeyValueModel> listPublishModel(@NotBlank String categoryId){
        return modelService.listPublishModel(categoryId);
    }
}
