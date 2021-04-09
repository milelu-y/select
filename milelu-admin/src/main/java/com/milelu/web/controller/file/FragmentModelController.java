package com.milelu.web.controller.file;

import java.util.List;
import java.util.Map;

import com.milelu.common.annotation.ResponseResult;
import com.milelu.common.core.domain.TreeFileModel;
import com.milelu.service.service.file.FragmentModelService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.milelu.common.annotation.Log;
import com.milelu.common.core.controller.BaseController;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.enums.BusinessType;
import com.milelu.service.domain.FragmentModel;
import com.milelu.common.utils.poi.ExcelUtil;
import com.milelu.common.core.page.TableDataInfo;

import javax.validation.constraints.NotBlank;

/**
 * 页面片段文件模型Controller
 *
 * @author MILELU
 * @date 2021-01-07
 */
@RestController
@RequestMapping("/fragment/model")
public class FragmentModelController extends BaseController
{
    @Autowired
    private FragmentModelService fragmentModelService;

    /**
     * 获取页面片段数
     */
    @PreAuthorize("@ss.hasPermi('fragment:model:list')")
    @GetMapping("/listTree")
    @ResponseResult(isAjaxResult = true)
    public List<TreeFileModel> listTree(){
        return fragmentModelService.loadTemplateTree();
    }

    /**
     * 查询页面片段文件模型列表
     */
    @PreAuthorize("@ss.hasPermi('fragment:model:list')")
    @GetMapping("/list")
    public TableDataInfo list(FragmentModel fragmentModel)
    {
        startPage();
        List<FragmentModel> list = fragmentModelService.selectFragmentModelList(fragmentModel);
        return getDataTable(list);
    }

    /**
     * 导出页面片段文件模型列表
     */
    @PreAuthorize("@ss.hasPermi('fragment:model:export')")
    @Log(title = "页面片段文件模型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FragmentModel fragmentModel)
    {
        List<FragmentModel> list = fragmentModelService.selectFragmentModelList(fragmentModel);
        ExcelUtil<FragmentModel> util = new ExcelUtil<FragmentModel>(FragmentModel.class);
        return util.exportExcel(list, "model");
    }

    /**
     * 获取页面片段文件模型详细信息
     */
    @PreAuthorize("@ss.hasPermi('fragment:model:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(fragmentModelService.selectFragmentModelById(id));
    }

    /**
     * 获取页面片段文件模型详细信息
     * @param fileName
     */
    @ResponseResult(isAjaxResult = true)
    @PreAuthorize("@ss.hasPermi('fragment:model:query')")
    @GetMapping(value = "/getFragmentInfo/{fileName}")
    public FragmentModel getFragmentInfo(@PathVariable("fileName") String fileName)
    {
        return fragmentModelService.selectFragmentModelByFileName(fileName);
    }

    /**
     * 新增页面片段文件模型
     */
    @PreAuthorize("@ss.hasPermi('fragment:model:add')")
    @Log(title = "页面片段文件模型", businessType = BusinessType.INSERT)
    @PostMapping
    @ResponseResult(isAjaxResult = true)
    public void add(@RequestBody FragmentModel fragmentModel)
    {
        fragmentModelService.insertFragmentModel(fragmentModel);
    }

    /**
     * 修改页面片段文件模型
     */
    @PreAuthorize("@ss.hasPermi('fragment:model:edit')")
    @Log(title = "页面片段文件模型", businessType = BusinessType.UPDATE)
    @PutMapping
    @ResponseResult(isAjaxResult = true)
    public void edit(@RequestBody FragmentModel fragmentModel)
    {
        fragmentModelService.updateFragmentModel(fragmentModel);
    }

    /**
     * 删除页面片段文件模型
     */
    @PreAuthorize("@ss.hasPermi('fragment:model:remove')")
    @Log(title = "页面片段文件模型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(fragmentModelService.deleteFragmentModelByIds(ids));
    }

    /**
     * 根据路径获取内容
     */
    @ResponseResult
    @GetMapping("/getFragmentContent")
    public String getFragmentContent(@RequestParam String path){
        return fragmentModelService.getFragmentContent(path);
    }

    /**
     * 设置片段内容
     */
    @Log(title = "设置页面片段内容", businessType = BusinessType.UPDATE)
    @ResponseResult(isAjaxResult = true)
    @PutMapping("/setFragmentContent")
    public void setFragmentContent(@RequestBody Map<String, String> map){
        fragmentModelService.saveFragmentContent(map.get("path"), map.get("content"));
    }

    /**
     * 根据路径删除页面片段
     * @param path
     */
    @ResponseResult(isAjaxResult = true)
    @Log(title = "删除模板", businessType = BusinessType.DELETE)
    @DeleteMapping("/delFragmentModel")
    public void deleteTemplate(@RequestParam("path") String path) {
         fragmentModelService.delFragment(path);
    }


    @GetMapping("/getFragmentForm")
    @ResponseResult(isAjaxResult = true)
    public Map<String,Object> getFragmentForm(@NotBlank @RequestParam("fileName") String fileName){

        return fragmentModelService.getFragmentForms(fileName);
    }
}
