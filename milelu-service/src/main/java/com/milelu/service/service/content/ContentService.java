package com.milelu.service.service.content;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.milelu.common.utils.PageBean;
import com.milelu.common.utils.model.DynamicModel;
import com.milelu.common.utils.model.Tree;
import com.milelu.service.domain.Category;
import com.milelu.service.domain.Content;
import com.milelu.service.domain.PublishDto;

/**
 * 内容Service接口
 *
 * @author MILELU
 * @date 2021-01-23
 */
public interface ContentService
{
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
    public void insertContent(Content content);

    /**
     * 修改内容
     *
     * @param content 内容
     * @return 结果
     */
    public void updateContent(Content content);

    /**
     * 批量删除内容
     *
     * @param ids 需要删除的内容ID
     * @return 结果
     */
    public void deleteContentByIds(String[] ids,boolean realDel);

    /**
     * 删除内容信息
     *
     * @param id 内容ID
     * @return 结果
     */
    public int deleteContentById(String id);

    /**
     * 查询内容列表
     */
    List<Content> selectContentLists(Content content);

    /**
     * 获取分类树
     * @return
     */
    Tree<Category> treeCategory();

    /**
     * 表单设计
     * @param modelId
     * @return
     */
    List<DynamicModel> getFormDesign(String modelId);
    /**
     * 根據内容ID 加載 内容詳情
     * @param params
     * @return
     */
    Map<String, Object> loadTempParams(Map<String, Object> params, List<String> asList);

    /**
     * 获取明细
     * @param id
     * @return
     */
    Map<String, Object> getDetail(String id);

    /**
     * 发布内容
     * @param publishDto
     * @param byJob
     */
    void publish(PublishDto publishDto, boolean byJob);

    /**
     * 加载置顶栏目内容列表
     * @return
     */
    Map<String, Object> listContent(Map<String, Object> params, Boolean notify);

    /**
     * 生成静态文件
     * @param ids
     */
    void reStaticBatchGenCid(List<String> ids);

    /**
     * 定时发布
     * @param ids
     * @param date
     */
    void jobPublish(List<String> ids, Date date);

    /**
     * 页面内容所有参数
     * @param pageNo
     * @param pageSize
     * @return
     */
    Map<String, Object> pageContentParamsForAllGen(Integer pageNo, Integer pageSize);

    /**
     * 获取指定栏目编码的内容
     * @param code
     * @param num
     * @param order
     * @return
     */
    List<Map<String, Object>> listByCode(String code, String categoryId, Integer num, String order);

    /**
     * 查询上一篇或者下一篇
     * @param id
     * @return
     */
    Map<String, Object> nextPrevious(String id, String categoryId);

    /**
     * 获取最新内容
     * @param categoryId
     * @param code
     * @param where
     * @return
     */
    Map<String, List> getTopNews(String categoryId, String code, String where);

    void top(Content content);

    PageBean<Content> questionList(int s, int e, String title);
}
