package com.milelu.service.service.content;

import java.util.List;
import java.util.Map;

import com.milelu.service.domain.ContentAttach;

/**
 * 内容附件Service接口
 *
 * @author MILELU
 * @date 2021-01-25
 */
public interface ContentAttachService
{
    /**
     * 查询内容附件
     *
     * @param id 内容附件ID
     * @return 内容附件
     */
    public ContentAttach selectContentAttachById(String id);

    /**
     * 查询内容附件列表
     *
     * @param contentAttach 内容附件
     * @return 内容附件集合
     */
    public List<ContentAttach> selectContentAttachList(ContentAttach contentAttach);

    /**
     * 新增内容附件
     *
     * @param contentAttach 内容附件
     * @return 结果
     */
    public int insertContentAttach(ContentAttach contentAttach);

    /**
     * 修改内容附件
     *
     * @param contentAttach 内容附件
     * @return 结果
     */
    public int updateContentAttach(ContentAttach contentAttach);

    /**
     * 批量删除内容附件
     *
     * @param ids 需要删除的内容附件ID
     * @return 结果
     */
    public int deleteContentAttachByIds(String[] ids);

    /**
     * 删除内容附件信息
     *
     * @param id 内容附件ID
     * @return 结果
     */
    public int deleteContentAttachById(String id);

    void deleteByFiled(String content_id, String contentId);

    List<String> listData(String id);

    List<Map> listAttachs(List<String> contentIds);

    List<Map> listAttach(String id);
}
