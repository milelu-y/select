package com.milelu.web.controller.content;

import java.util.List;
import java.util.Map;

import com.milelu.common.annotation.ResponseResult;
import com.milelu.common.utils.PageBean;
import com.milelu.common.utils.model.DynamicModel;
import com.milelu.common.utils.model.Tree;
import com.milelu.service.domain.Category;
import com.milelu.service.domain.PublishDto;
import com.milelu.service.service.content.ContentService;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.milelu.common.annotation.Log;
import com.milelu.common.core.controller.BaseController;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.enums.BusinessType;
import com.milelu.service.domain.Content;
import com.milelu.common.utils.poi.ExcelUtil;
import com.milelu.common.core.page.TableDataInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * 内容Controller
 *
 * @author MILELU
 * @date 2021-01-23
 */
@RestController
@RequestMapping("/content/manage")
public class ContentController extends BaseController {
    @Autowired
    private ContentService contentService;

    /**
     * 查询内容列表
     */
    @PreAuthorize("@ss.hasPermi('content:manage:list')")
    @GetMapping("/list")
    public TableDataInfo list(Content content) {
        startPage();
        List<Content> list = contentService.selectContentList(content);
        return getDataTable(list);
    }

    /**
     * 查询内容列表
     */
    @PreAuthorize("@ss.hasPermi('content:manage:list')")
    @GetMapping("/lists")
    public TableDataInfo lists(Content content) {
        startPage();
        List<Content> list = contentService.selectContentLists(content);
        return getDataTable(list);
    }

    /**
     * 导出内容列表
     */
    @PreAuthorize("@ss.hasPermi('content:manage:export')")
    @Log(title = "内容", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Content content) {
        List<Content> list = contentService.selectContentList(content);
        ExcelUtil<Content> util = new ExcelUtil<Content>(Content.class);
        return util.exportExcel(list, "manage");
    }

    /**
     * 获取内容详细信息
     */
    @PreAuthorize("@ss.hasPermi('content:manage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(contentService.selectContentById(id));
    }

    /**
     * 新增内容
     */
    @PreAuthorize("@ss.hasPermi('content:manage:add')")
    @Log(title = "添加内容", businessType = BusinessType.INSERT)
    @PostMapping
    @ResponseResult(isAjaxResult = true)
    public void add(@RequestBody Content content) {
        contentService.insertContent(content);
    }

    /**
     * 修改内容
     */
    @PreAuthorize("@ss.hasPermi('content:manage:edit')")
    @Log(title = "修改内容", businessType = BusinessType.UPDATE)
    @PutMapping
    @ResponseResult(isAjaxResult = true)
    public void edit(@RequestBody Content content) {
        contentService.updateContent(content);
    }

    /**
     * 删除内容
     */
    @PreAuthorize("@ss.hasPermi('content:manage:remove')")
    @Log(title = "内容", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ResponseResult(isAjaxResult = true)
    public void remove(@PathVariable String[] ids) {
        contentService.deleteContentByIds(ids, false);
    }

    @GetMapping("/treeCategory")
    @ResponseResult(isAjaxResult = true)
    public Tree<Category> treeCategory() {
        Tree<Category> treeCategorys = contentService.treeCategory();
        return treeCategorys;
    }

    @GetMapping("/getFormDesign")
    @ResponseResult(isAjaxResult = true)
    public List<DynamicModel> getFormDesign(@NotBlank @RequestParam String modelId) {
        return contentService.getFormDesign(modelId);
    }

    @GetMapping("getDetail")
    @ResponseResult(isAjaxResult = true)
    public Map<String, Object> getDetail(@NotBlank @RequestParam String id) {
        return contentService.getDetail(id);
    }

    @Log(title = "内容发布", businessType = BusinessType.OTHER)
    @PutMapping(value = "publish")
    @ResponseResult(isAjaxResult = true)
    public void publish(@Validated @RequestBody PublishDto publishDto) {
        contentService.publish(publishDto, false);
    }

    @Log(title = "批量生成内容精通页面", businessType = BusinessType.OTHER)
    @PostMapping("/reStaticBatchGenCid")
    @ResponseResult(isAjaxResult = true)
    public void reStaticFileByCid(@NotEmpty @RequestBody List<String> ids) {
        contentService.reStaticBatchGenCid(ids);
    }

    @Log(title = "置顶内容", businessType = BusinessType.OTHER)
    @PutMapping("top")
    @ResponseResult(isAjaxResult = true)
    public void top(@RequestBody Content content) {
        contentService.top(content);
    }

    /**
     * 门户端获取搜索内容
     */
    @PostMapping("/searchContent")
    @ResponseResult(isAjaxResult = true)
    public PageBean<Content> searchContent(@RequestBody Bean bean) {
        return contentService.questionList(bean.getStart()-1, bean.getEnd(), bean.getKeywords());
    }
}

@Data
class Bean {
    private int start;
    private int end;
    private String keywords;
}
