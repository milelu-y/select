package com.milelu.service.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.Page;
import com.milelu.service.domain.Content;
import org.apache.ibatis.annotations.Param;

/**
 * 内容Mapper接口
 *
 * @author MILELU
 * @date 2021-01-23
 */
public interface ContentMapper {
    /**
     * 查询内容
     *
     * @param id 内容ID
     * @return 内容
     */
    public Content selectContentById(String id);

    /**
     * 查询内容列表
     *
     * @param content 内容
     * @return 内容集合
     */
    public List<Content> selectContentList(Content content);

    /**
     * 新增内容
     *
     * @param content 内容
     * @return 结果
     */
    public int insertContent(Content content);

    /**
     * 修改内容
     *
     * @param content 内容
     * @return 结果
     */
    public int updateContent(Content content);

    /**
     * 删除内容
     *
     * @param id 内容ID
     * @return 结果
     */
    public int deleteContentById(String id);

    /**
     * 批量删除内容
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteContentByIds(String[] ids);

    List<Content> selectContentLists(@Param("dto") Content content);

    String getPathRule(String id);

    Map<String, Object> loadTempParams(@Param("contentId") String contentId, @Param("statues") List<String> status);

    List<String> listCategoryByCids(@Param("ids") List<String> ids);

    Integer publish(@Param("ids") List<String> ids,
                    @Param("status") String status,
                    @Param("date") Date date,
                    @Param("byJob") Boolean byJob);

    String getCategoryId(@Param("id") String id);

    Map<String, Object> nextPrevious(@Param("id") String id, @Param("next") Boolean next, @Param("categoryId") String categoryId);

    Page<Map<String, Object>> pageContent(@Param("pages") IPage<Map<String, Object>> pages,
                                          @Param("categoryId") String categoryId);

    List<Map<String, Object>> buildParams(@Param("ids") List<String> ids);

    void jobPublish(@Param("contentIds") List<String> ids, @Param("date") Date date);

    void updateStatus(@Param("id") String id, @Param("status") String status);

    Page<Map<String, String>> pageAllContentForGen(IPage<Map<String, String>> pages);

    List<Map<String, Object>> listByCode(@Param("code") String code,
                                         @Param("categoryId") String categoryId,
                                         @Param("num") Integer num,
                                         @Param("ascFields") List<String> asc,
                                         @Param("descFields") List<String> desc);

    List<Map<String, Object>> getTopNews(@Param("categoryId") String categoryId,
                                         @Param("codes") List<String> codes,
                                         @Param("field") String field,
                                         @Param("num") Integer num);

    void top(Content content);

    Integer count();

    List<Content> questionList(@Param("start") int start, @Param("end") Integer end, @Param("keywords") String keywords);
}
