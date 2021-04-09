package com.milelu.service.mapper;

import java.util.List;

import com.milelu.service.domain.ContentAttribute;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
 * 内容扩展Mapper接口
 *
 * @author MILELU
 * @date 2021-01-25
 */
public interface ContentAttributeMapper {
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
     * 删除内容扩展
     *
     * @param id 内容扩展ID
     * @return 结果
     */
    public int deleteContentAttributeById(String id);

    /**
     * 批量删除内容扩展
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteContentAttributeByIds(String[] ids);

    ContentAttribute getByField(@Param("field") Object field, @Param("val") String val);

    void deleteByFiled(@Param("field") String field, @Param("val") String val);
}
