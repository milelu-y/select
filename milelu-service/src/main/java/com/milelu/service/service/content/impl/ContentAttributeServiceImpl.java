package com.milelu.service.service.content.impl;

import java.util.List;

import com.milelu.service.service.content.ContentAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.service.mapper.ContentAttributeMapper;
import com.milelu.service.domain.ContentAttribute;

/**
 * 内容扩展Service业务层处理
 *
 * @author MILELU
 * @date 2021-01-25
 */
@Service
public class ContentAttributeServiceImpl implements ContentAttributeService
{
    @Autowired
    private ContentAttributeMapper contentAttributeMapper;

    /**
     * 查询内容扩展
     *
     * @param id 内容扩展ID
     * @return 内容扩展
     */
    @Override
    public ContentAttribute selectContentAttributeById(String id)
    {
        return contentAttributeMapper.selectContentAttributeById(id);
    }

    /**
     * 查询内容扩展列表
     *
     * @param contentAttribute 内容扩展
     * @return 内容扩展
     */
    @Override
    public List<ContentAttribute> selectContentAttributeList(ContentAttribute contentAttribute)
    {
        return contentAttributeMapper.selectContentAttributeList(contentAttribute);
    }

    /**
     * 新增内容扩展
     *
     * @param contentAttribute 内容扩展
     * @return 结果
     */
    @Override
    public int insertContentAttribute(ContentAttribute contentAttribute)
    {
        return contentAttributeMapper.insertContentAttribute(contentAttribute);
    }

    /**
     * 修改内容扩展
     *
     * @param contentAttribute 内容扩展
     * @return 结果
     */
    @Override
    public int updateContentAttribute(ContentAttribute contentAttribute)
    {
        return contentAttributeMapper.updateContentAttribute(contentAttribute);
    }

    /**
     * 批量删除内容扩展
     *
     * @param ids 需要删除的内容扩展ID
     * @return 结果
     */
    @Override
    public int deleteContentAttributeByIds(String[] ids)
    {
        return contentAttributeMapper.deleteContentAttributeByIds(ids);
    }

    /**
     * 删除内容扩展信息
     *
     * @param id 内容扩展ID
     * @return 结果
     */
    @Override
    public int deleteContentAttributeById(String id)
    {
        return contentAttributeMapper.deleteContentAttributeById(id);
    }


    @Override
    public ContentAttribute getByField(String field, String val) {
        return  contentAttributeMapper.getByField(field,val);
    }

    @Override
    public void deleteByFiled(String field, String val) {
        contentAttributeMapper.deleteByFiled(field,val);
    }
}
