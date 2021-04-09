package com.milelu.web.controller.content;

import java.util.List;

import com.milelu.service.service.content.ContentAttributeService;
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
import com.milelu.service.domain.ContentAttribute;
import com.milelu.common.utils.poi.ExcelUtil;
import com.milelu.common.core.page.TableDataInfo;

/**
 * 内容扩展Controller
 *
 * @author MILELU
 * @date 2021-01-25
 */
@RestController
@RequestMapping("/content/attribute")
public class ContentAttributeController extends BaseController
{
    @Autowired
    private ContentAttributeService contentAttributeService;

    /**
     * 查询内容扩展列表
     */
//    @PreAuthorize("@ss.hasPermi('content:attribute:list')")
    @GetMapping("/list")
    public TableDataInfo list(ContentAttribute contentAttribute)
    {
        startPage();
        List<ContentAttribute> list = contentAttributeService.selectContentAttributeList(contentAttribute);
        return getDataTable(list);
    }

    /**
     * 导出内容扩展列表
     */
//    @PreAuthorize("@ss.hasPermi('content:attribute:export')")
    @Log(title = "内容扩展", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ContentAttribute contentAttribute)
    {
        List<ContentAttribute> list = contentAttributeService.selectContentAttributeList(contentAttribute);
        ExcelUtil<ContentAttribute> util = new ExcelUtil<ContentAttribute>(ContentAttribute.class);
        return util.exportExcel(list, "attribute");
    }

    /**
     * 获取内容扩展详细信息
     */
//    @PreAuthorize("@ss.hasPermi('content:attribute:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(contentAttributeService.selectContentAttributeById(id));
    }

    /**
     * 新增内容扩展
     */
//    @PreAuthorize("@ss.hasPermi('content:attribute:add')")
    @Log(title = "内容扩展", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ContentAttribute contentAttribute)
    {
        return toAjax(contentAttributeService.insertContentAttribute(contentAttribute));
    }

    /**
     * 修改内容扩展
     */
//    @PreAuthorize("@ss.hasPermi('content:attribute:edit')")
    @Log(title = "内容扩展", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ContentAttribute contentAttribute)
    {
        return toAjax(contentAttributeService.updateContentAttribute(contentAttribute));
    }

    /**
     * 删除内容扩展
     */
//    @PreAuthorize("@ss.hasPermi('content:attribute:remove')")
    @Log(title = "内容扩展", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(contentAttributeService.deleteContentAttributeByIds(ids));
    }
}
