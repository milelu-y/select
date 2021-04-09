package com.milelu.service.mapper;

import java.util.List;
import java.util.Map;

import com.milelu.service.domain.ContentAttach;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
 * 内容附件Mapper接口
 *
 * @author MILELU
 * @date 2021-01-25
 */
public interface ContentAttachMapper
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
     * 删除内容附件
     *
     * @param id 内容附件ID
     * @return 结果
     */
    public int deleteContentAttachById(String id);

    /**
     * 批量删除内容附件
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteContentAttachByIds(String[] ids);

    void deleteByFiled(@Param("field") String field, @Param("val") String val);

    List<String> listData(@Param("contentId") String id);

    List<Map> listAttachs(@Param("contentIds") List<String> contentIds);

    List<Map> listAttach(@Param("contentId") String contentId);
}
