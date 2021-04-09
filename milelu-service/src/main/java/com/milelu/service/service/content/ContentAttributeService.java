package com.milelu.service.service.content;

import java.util.List;
import com.milelu.service.domain.ContentAttribute;

/**
 * 内容扩展Service接口
 *
 * @author MILELU
 * @date 2021-01-25
 */
public interface ContentAttributeService
{
    /**
     * 查询内容扩展
     *
     * @param id 内容扩展ID
     * @return 内容扩展
     */
    public ContentAttribute selectContentAttributeById(String id);

    /**
     * 查询内容扩展列表
     *
     * @param contentAttribute 内容扩展
     * @return 内容扩展集合
     */
    public List<ContentAttribute> selectContentAttributeList(ContentAttribute contentAttribute);

    /**
     * 新增内容扩展
     *
     * @param contentAttribute 内容扩展
     * @return 结果
     */
    public int insertContentAttribute(ContentAttribute contentAttribute);

    /**
     * 修改内容扩展
     *
     * @param contentAttribute 内容扩展
     * @return 结果
     */
    public int updateContentAttribute(ContentAttribute contentAttribute);

    /**
     * 批量删除内容扩展
     *
     * @param ids 需要删除的内容扩展ID
     * @return 结果
     */
    public int deleteContentAttributeByIds(String[] ids);

    /**
     * 删除内容扩展信息
     *
     * @param id 内容扩展ID
     * @return 结果
     */
    public int deleteContentAttributeById(String id);

    /**
     * 根据字段查询
     * @param field
     * @param val
     * @return
     */
    ContentAttribute getByField(String field, String val);

    void deleteByFiled(String content_id, String contentId);
}
