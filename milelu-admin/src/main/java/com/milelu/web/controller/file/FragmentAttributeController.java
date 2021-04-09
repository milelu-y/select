package com.milelu.web.controller.file;

import java.util.List;
import java.util.Map;

import com.milelu.common.annotation.ResponseResult;
import com.milelu.service.service.file.FragmentAttributeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.milelu.common.annotation.Log;
import com.milelu.common.core.controller.BaseController;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.enums.BusinessType;
import com.milelu.service.domain.FragmentAttribute;
import com.milelu.common.utils.poi.ExcelUtil;
import com.milelu.common.core.page.TableDataInfo;

import javax.validation.constraints.NotBlank;

/**
 * 页面片段数据Controller
 *
 * @author MILELU
 * @date 2021-01-11
 */
@RestController
@RequestMapping("/fragment/attribute")
public class FragmentAttributeController extends BaseController {
    @Autowired
    private FragmentAttributeService fragmentAttributeService;

    /**
     * 查询页面片段数据列表
     */
//    @PreAuthorize("@ss.hasPermi('fragment:attribute:list')")
    @GetMapping("/list")
    public TableDataInfo list(FragmentAttribute fragmentAttribute) {
        startPage();
        List<FragmentAttribute> list = fragmentAttributeService.selectFragmentAttributeList(fragmentAttribute);
        return getDataTable(list);
    }

    /**
     * 导出页面片段数据列表
     */
//    @PreAuthorize("@ss.hasPermi('fragment:attribute:export')")
    @Log(title = "页面片段数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FragmentAttribute fragmentAttribute) {
        List<FragmentAttribute> list = fragmentAttributeService.selectFragmentAttributeList(fragmentAttribute);
        ExcelUtil<FragmentAttribute> util = new ExcelUtil<FragmentAttribute>(FragmentAttribute.class);
        return util.exportExcel(list, "attribute");
    }

    /**
     * 获取页面片段数据详细信息
     */
//    @PreAuthorize("@ss.hasPermi('fragment:attribute:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(fragmentAttributeService.selectFragmentAttributeById(id));
    }

    /**
     * 新增页面片段数据
     */
//    @PreAuthorize("@ss.hasPermi('fragment:attribute:add')")
    @Log(title = "页面片段数据", businessType = BusinessType.INSERT)
    @PostMapping
    @ResponseResult(isAjaxResult = true)
    public void add(@RequestBody FragmentAttribute fragmentAttribute) {
        fragmentAttributeService.insertFragmentAttribute(fragmentAttribute);
    }

    /**
     * 修改页面片段数据
     */
//    @PreAuthorize("@ss.hasPermi('fragment:attribute:edit')")
    @Log(title = "页面片段数据", businessType = BusinessType.UPDATE)
    @PutMapping
    @ResponseResult(isAjaxResult = true)
    public void edit(@RequestBody FragmentAttribute fragmentAttribute) {
        fragmentAttributeService.updateFragmentAttribute(fragmentAttribute);
    }

    /**
     * 删除页面片段数据
     */
//    @PreAuthorize("@ss.hasPermi('fragment:attribute:remove')")
    @Log(title = "页面片段数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(fragmentAttributeService.deleteFragmentAttributeByIds(ids));
    }

    /**
     * 获取
     */
    @GetMapping("/getDesignAttr/{id}")
    public Map<String, Object> getDesignAttrById(@NotBlank @PathVariable("id") String id) {
        return fragmentAttributeService.getDesignAttrById(id);
    }

}
