package com.milelu.web.controller.content;

import java.util.List;

import com.milelu.service.service.content.ContentAttachService;
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
import com.milelu.service.domain.ContentAttach;
import com.milelu.common.utils.poi.ExcelUtil;
import com.milelu.common.core.page.TableDataInfo;

/**
 * 内容附件Controller
 *
 * @author MILELU
 * @date 2021-01-25
 */
@RestController
@RequestMapping("/content/attach")
public class ContentAttachController extends BaseController
{
    @Autowired
    private ContentAttachService contentAttachService;

    /**
     * 查询内容附件列表
     */
    @PreAuthorize("@ss.hasPermi('content:attach:list')")
    @GetMapping("/list")
    public TableDataInfo list(ContentAttach contentAttach)
    {
        startPage();
        List<ContentAttach> list = contentAttachService.selectContentAttachList(contentAttach);
        return getDataTable(list);
    }

    /**
     * 导出内容附件列表
     */
    @PreAuthorize("@ss.hasPermi('content:attach:export')")
    @Log(title = "内容附件", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ContentAttach contentAttach)
    {
        List<ContentAttach> list = contentAttachService.selectContentAttachList(contentAttach);
        ExcelUtil<ContentAttach> util = new ExcelUtil<ContentAttach>(ContentAttach.class);
        return util.exportExcel(list, "attach");
    }

    /**
     * 获取内容附件详细信息
     */
    @PreAuthorize("@ss.hasPermi('content:attach:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(contentAttachService.selectContentAttachById(id));
    }

    /**
     * 新增内容附件
     */
    @PreAuthorize("@ss.hasPermi('content:attach:add')")
    @Log(title = "内容附件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ContentAttach contentAttach)
    {
        return toAjax(contentAttachService.insertContentAttach(contentAttach));
    }

    /**
     * 修改内容附件
     */
    @PreAuthorize("@ss.hasPermi('content:attach:edit')")
    @Log(title = "内容附件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ContentAttach contentAttach)
    {
        return toAjax(contentAttachService.updateContentAttach(contentAttach));
    }

    /**
     * 删除内容附件
     */
    @PreAuthorize("@ss.hasPermi('content:attach:remove')")
    @Log(title = "内容附件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(contentAttachService.deleteContentAttachByIds(ids));
    }
}
