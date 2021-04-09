package com.milelu.service.service.content.impl;

import java.util.List;
import java.util.Map;

import com.milelu.service.service.content.ContentAttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.service.mapper.ContentAttachMapper;
import com.milelu.service.domain.ContentAttach;

/**
 * 内容附件Service业务层处理
 *
 * @author MILELU
 * @date 2021-01-25
 */
@Service
public class ContentAttachServiceImpl implements ContentAttachService
{
    @Autowired
    private ContentAttachMapper contentAttachMapper;

    /**
     * 查询内容附件
     *
     * @param id 内容附件ID
     * @return 内容附件
     */
    @Override
    public ContentAttach selectContentAttachById(String id)
    {
        return contentAttachMapper.selectContentAttachById(id);
    }

    /**
     * 查询内容附件列表
     *
     * @param contentAttach 内容附件
     * @return 内容附件
     */
    @Override
    public List<ContentAttach> selectContentAttachList(ContentAttach contentAttach)
    {
        return contentAttachMapper.selectContentAttachList(contentAttach);
    }

    /**
     * 新增内容附件
     *
     * @param contentAttach 内容附件
     * @return 结果
     */
    @Override
    public int insertContentAttach(ContentAttach contentAttach)
    {
        return contentAttachMapper.insertContentAttach(contentAttach);
    }

    /**
     * 修改内容附件
     *
     * @param contentAttach 内容附件
     * @return 结果
     */
    @Override
    public int updateContentAttach(ContentAttach contentAttach)
    {
        return contentAttachMapper.updateContentAttach(contentAttach);
    }

    /**
     * 批量删除内容附件
     *
     * @param ids 需要删除的内容附件ID
     * @return 结果
     */
    @Override
    public int deleteContentAttachByIds(String[] ids)
    {
        return contentAttachMapper.deleteContentAttachByIds(ids);
    }

    /**
     * 删除内容附件信息
     *
     * @param id 内容附件ID
     * @return 结果
     */
    @Override
    public int deleteContentAttachById(String id)
    {
        return contentAttachMapper.deleteContentAttachById(id);
    }


    @Override
    public void deleteByFiled(String field, String contentId) {
        contentAttachMapper.deleteByFiled(field,contentId);
    }

    @Override
    public List<String> listData(String id) {
        return contentAttachMapper.listData(id);
    }

    @Override
    public List<Map> listAttachs(List<String> contentIds) {

        return contentAttachMapper.listAttachs(contentIds);
    }

    @Override
    public List<Map> listAttach(String contentId) {
        return contentAttachMapper.listAttach(contentId);
    }
}
