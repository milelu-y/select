package com.milelu.service.mapper;

import java.util.List;
import java.util.Map;

import com.milelu.common.utils.model.Tree;
import com.milelu.service.domain.Category;
import com.milelu.service.domain.CategoryNavbar;
import org.apache.ibatis.annotations.Param;

/**
 * 分类Mapper接口
 *
 * @author MILELU
 * @date 2021-01-18
 */
public interface CategoryMapper
{
    /**
     * 查询分类
     *
     * @param id 分类ID
     * @return 分类
     */
    public Category selectCategoryById(String id);

    /**
     * 查询分类列表
     *
     * @param category 分类
     * @return 分类集合
     */
    public List<Category> selectCategoryList(Category category);

    /**
     * 新增分类
     *
     * @param category 分类
     * @return 结果
     */
    public int insertCategory(Category category);

    /**
     * 修改分类
     *
     * @param category 分类
     * @return 结果
     */
    public int updateCategory(Category category);

    /**
     * 删除分类
     *
     * @param id 分类ID
     * @return 结果
     */
    public int deleteCategoryById(String id);

    /**
     * 批量删除分类
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCategoryByIds(String[] ids);

    /**
     * 树结构
     * @return
     */
    List<Tree<Category>> treeCategory();

    /**
     * 检查编码是否存在
     * @param code
     * @return
     */
    Integer checkMaxCode(String code);

    /**
     * 获取明细
     * @param id
     * @return
     */
    Category getDetail(String id);

    /**
     * 获取模板路径
     * @param id
     * @return
     */
    String getCategoryTempPath(String id);

    /**
     * 查询子栏目个数
     * @return
     */
    int selectChildCount(@Param("parentId") String parentId);

    Map<String, Object> getMapDetail(@Param("id") String id, @Param("code")String code, @Param("siteId")String  siteId);

    List<Category> listCategoryByPid(String categoryId);

    List<Category> treeCategoryForContent();

    List<String> getIds();

    List<CategoryNavbar> navbar(@Param("showHideItem")boolean showHideItem);

    Integer countContent(@Param("code") String code, @Param("categoryId") String id);

    List<Map<String, Object>> getMapDetails(String id);

    List<Category> selectCategoryListParent(Category category);
}
