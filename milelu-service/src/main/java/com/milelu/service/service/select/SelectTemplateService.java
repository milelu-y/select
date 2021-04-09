package com.milelu.service.service.select;

import java.util.List;
import com.milelu.service.domain.SelectTemplate;

/**
 * 选版模板Service接口
 *
 * @author MILELU
 * @date 2021-04-09
 */
public interface SelectTemplateService
{
    /**
     * 查询选版模板
     *
     * @param id 选版模板ID
     * @return 选版模板
     */
    public SelectTemplate selectSelectTemplateById(Long id);

    /**
     * 查询选版模板列表
     *
     * @param selectTemplate 选版模板
     * @return 选版模板集合
     */
    public List<SelectTemplate> selectSelectTemplateList(SelectTemplate selectTemplate);

    /**
     * 新增选版模板
     *
     * @param selectTemplate 选版模板
     * @return 结果
     */
    public int insertSelectTemplate(SelectTemplate selectTemplate);

    /**
     * 修改选版模板
     *
     * @param selectTemplate 选版模板
     * @return 结果
     */
    public int updateSelectTemplate(SelectTemplate selectTemplate);

    /**
     * 批量删除选版模板
     *
     * @param ids 需要删除的选版模板ID
     * @return 结果
     */
    public int deleteSelectTemplateByIds(Long[] ids);

    /**
     * 删除选版模板信息
     *
     * @param id 选版模板ID
     * @return 结果
     */
    public int deleteSelectTemplateById(Long id);
}
