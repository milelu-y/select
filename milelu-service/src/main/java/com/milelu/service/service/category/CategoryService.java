package com.milelu.service.service.category;

import java.util.List;
import java.util.Map;

import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.utils.model.KeyValueModel;
import com.milelu.common.utils.model.Tree;
import com.milelu.service.domain.Category;
import com.milelu.service.domain.CategoryNavbar;

/**
 * 分类Service接口
 *
 * @author MILELU
 * @date 2021-01-18
 */
public interface CategoryService
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
    public AjaxResult insertCategory(Category category);

    /**
     * 修改分类
     *
     * @param category 分类
     * @return 结果
     */
    public void updateCategory(Category category);

    /**
     * 批量删除分类
     *
     * @param ids 需要删除的分类ID
     * @return 结果
     */
    public int deleteCategoryByIds(String[] ids);

    /**
     * 删除分类信息
     *
     * @param id 分类ID
     * @return 结果
     */
    public int deleteCategoryById(String id);

    /**
     * 查询分类树
     * @return
     */
    Tree<Category> treeCategory();

    /**
     * 获取路径规则
     * @return
     */
    List<KeyValueModel> loadPathRule();

    /**
     * 获取模板文件
     * @return
     */
    Map<String, Object> loadTemplate();

    /**
     * 获取分类详情
     * @param id
     * @return
     */
    Map<String, Object> getDetail(String id);

    /**
     * 根据Id删除
     * @param id
     * @return
     */
    void deleteById(String id);

    /**
     * 生成栏目
     * @param id
     */
    void genCategory(String id);

    /**
     * 加载参数
     * @param categoryId
     * @return
     */
    Map<String, Object> loadTempParams(String categoryId);

    List<Category> listCategoryByPid(String categoryId);

    Tree<Category> treeCategoryForContent();

    Category getDetailById(String categoryId);

    List<String> getIds();

    List<CategoryNavbar> navbar(String code, Boolean showCount, Boolean showHideMenu);

    Map<String, Object> info(String code, String id);

    /**
     * 获取面包屑
     * @param id
     * @param postfix
     * @param containit
     * @return
     */
    List<Category> breadCrumbs(String id, String postfix, Boolean containit);
}
