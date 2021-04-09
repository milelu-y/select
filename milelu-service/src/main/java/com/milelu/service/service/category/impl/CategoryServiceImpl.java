package com.milelu.service.service.category.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Lists;
import com.milelu.common.config.MileluConfig;
import com.milelu.common.constant.Channel;
import com.milelu.common.constant.Constants;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.core.domain.TreeFileModel;
import com.milelu.common.enums.PathRule;
import com.milelu.common.exception.CustomException;
import com.milelu.common.utils.*;
import com.milelu.common.utils.model.DynamicModel;
import com.milelu.common.utils.model.KeyValueModel;
import com.milelu.common.utils.model.Tree;
import com.milelu.freemark.emums.ChannelEnum;
import com.milelu.freemark.processor.message.MessageSend;
import com.milelu.service.domain.CategoryAttribute;
import com.milelu.service.domain.CategoryNavbar;
import com.milelu.service.domain.CategoryTemplate;
import com.milelu.service.service.category.*;
import com.milelu.service.service.model.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.service.mapper.CategoryMapper;
import com.milelu.service.domain.Category;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import static com.milelu.common.utils.uuid.SnowflakeIdWorker.getId;

/**
 * 分类Service业务层处理
 *
 * @author MILELU
 * @date 2021-01-18
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    MileluConfig mileluConfig;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    ModelService modelService;

    @Autowired
    CategoryModelService categoryModelService;

    @Autowired
    CategoryTemplateService categoryTemplateService;

    @Autowired
    CategoryAttributeService categoryAttributeService;

    @Autowired
    CategoryModelRelationService categoryModelRelationService;

    @Autowired
    MessageSend messageSend;

    /**
     * 查询分类
     *
     * @param id 分类ID
     * @return 分类
     */
    @Override
    public Category selectCategoryById(String id) {

        return categoryMapper.selectCategoryById(id);
    }

    /**
     * 查询分类列表
     *
     * @param category 分类
     * @return 分类
     */
    @Override
    public List<Category> selectCategoryList(Category category) {
        return categoryMapper.selectCategoryListParent(category);
    }

    /**
     * 新增分类
     *
     * @param category 分类
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public AjaxResult insertCategory(Category category) {
        category.setCreateTime(DateUtils.getNowDate())
                .setCreateId(SecurityUtils.getLoginUser().getUser().getUserId().toString());
        checkLegal(category, true);
        categoryMapper.insertCategory(category);
        saveUpdateAttr(category, true);
        saveUpdateTemplate(category, true);
        return AjaxResult.success(category.getId());
    }

    private void saveUpdateTemplate(Category category, boolean save) {
        // 查询当前网站的默认模板
        if (!category.getOnlyUrl()) {
            if (save) {
                saveTemplate(category);
            } else {
                checkIsSaveOrUp(category);
            }
        }
    }

    private void checkIsSaveOrUp(Category v) {
        String templateId = "1234567890";
        CategoryTemplate categoryTemplate = categoryTemplateService.getCategoryTemp(v.getId(), templateId);
        if (CommonUtils.BeNotNull(categoryTemplate)) {
            String tempPath = v.getTemplatePath().replace("\\", Constants.SEPARATOR);
            if (!tempPath.equals(categoryTemplate.getTemplatePath())) {
                categoryTemplate.setTemplatePath(tempPath.replace(mileluConfig.getTemplate(), ""));
                categoryTemplateService.updateCategoryTemplate(categoryTemplate);
            }
        } else {
            saveTemplate(v);
        }
    }

    private void saveUpdateAttr(Category category, boolean save) {
        String attrData = "";
        if (CommonUtils.BeNotBlank(category.getCategoryModelId())) {
            List<DynamicModel> dynamicModels = getModelField(category);
            attrData = ModelFieldUtil.modelToJsonStr(dynamicModels);
        }
        if (save) {
            CategoryAttribute categoryAttrDto = new CategoryAttribute();
            categoryAttrDto.setCategoryId(category.getId()).setData(attrData);
            categoryAttributeService.insertCategoryAttribute(categoryAttrDto);
        } else {
            CategoryAttribute categoryAttrDto = new CategoryAttribute();
            categoryAttrDto.setCategoryId(category.getId()).setData(attrData);
            categoryAttributeService.updateCategoryAttribute(categoryAttrDto);
        }
    }

    private List<DynamicModel> getModelField(Category category) {
        List<DynamicModel> dynamicModels = null;
        String fieldJson = categoryModelService.getDesignField(category.getCategoryModelId());
        if (CommonUtils.BeNotBlank(fieldJson)) {
            dynamicModels = ModelFieldUtil.listExtendModel(fieldJson);
        }
        List<DynamicModel> resultFields = CommonUtils.BeNotEmpty(dynamicModels) ? dynamicModels : Lists.newArrayList();
        ModelFieldUtil.filterMapToSetFieldValue(resultFields, category.getParams());
        return resultFields;
    }

    private void checkLegal(Category category, boolean save) {
        if (save) {
            Integer count = categoryMapper.checkMaxCode(category.getCode());
            if (count > 0) {
                throw new CustomException("栏目编码已存在！！");
            }
            if (CommonUtils.BeBlank(category.getId())) {
                category.setId(getId());
            }
        }
        if (category.getOnlyUrl()) {
            category.setPathRule("").setPath("").setTemplatePath("").setPageSize(0L).setTopPages(0);
        }
    }

    private void saveTemplate(Category v) {
        CategoryTemplate templateDto = new CategoryTemplate();
        templateDto.setCategoryId(v.getId()).setTemplateId("1234567890").
                setTemplatePath(v.getTemplatePath().replace("\\", Constants.SEPARATOR)
                        .replace(mileluConfig.getTemplate(), ""))
                .setId(getId())
                .setCreateId(SecurityUtils.getLoginUser().getUser().getUserId().toString())
                .setCreateTime(DateUtils.getNowDate());
        categoryTemplateService.insertCategoryTemplate(templateDto);
    }

    /**
     * 修改分类
     *
     * @param category 分类
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateCategory(Category category) {
        checkLegal(category, false);
        categoryMapper.updateCategory(category);
        saveUpdateAttr(category, false);
        saveUpdateTemplate(category, false);
    }

    /**
     * 批量删除分类
     *
     * @param ids 需要删除的分类ID
     * @return 结果
     */
    @Override
    public int deleteCategoryByIds(String[] ids) {
        return categoryMapper.deleteCategoryByIds(ids);
    }

    /**
     * 删除分类信息
     *
     * @param id 分类ID
     * @return 结果
     */
    @Override
    public int deleteCategoryById(String id) {
        return categoryMapper.deleteCategoryById(id);
    }

    /**
     * 查询分类树
     *
     * @return
     */
    @Override
    public Tree<Category> treeCategory() {
        List<Tree<Category>> trees = categoryMapper.treeCategory();
        return getCategoryTree(trees);
    }

    /**
     * 获取路径规则
     *
     * @return
     */
    @Override
    public List<KeyValueModel> loadPathRule() {
        return PathRule.keyValues();
    }

    /**
     * 获取模板文件
     *
     * @return
     */
    @Override
    public Map<String, Object> loadTemplate() {
        List<TreeFileModel> templateTree = modelService.loadTemplateTree();
        Map<String, Object> result = new HashMap<>(16);
        result.put("templateFiles", templateTree);
        return result;
    }

    /**
     * 获取分类详情
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getDetail(String id) {
        Map<String, Object> result = new HashMap<>(16);
        Category category = categoryMapper.getDetail(id);
        result.put("category", category);
        handModels(category, result);
        handSetTemplate(id, result);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        checkLegal(id);
        categoryAttributeService.deleteByFiled("category_id", id);
        categoryTemplateService.deleteByFiled("category_id", id);
        categoryModelRelationService.deleteByFiled("category_id", id);
        categoryMapper.deleteCategoryById(id);
    }


    private void checkLegal(String id) {
        if ("0".equals(id)) {
            throw new CustomException("该栏目禁止删除");
        }
        if (checkHasChild(id)) {
            throw new CustomException("该栏目下存在子栏目,请先删除子栏目后再删除本栏目!");
        }
    }

    private boolean checkHasChild(String id) {
        return categoryMapper.selectChildCount(id) > 0;
    }

    private void handSetTemplate(String id, Map<String, Object> result) {
        String templatePath = categoryMapper.getCategoryTempPath(id);
        if (CommonUtils.BeNotBlank(templatePath)) {
            String finaPath = mileluConfig.getTemplate() + templatePath;
            result.put("templatePath", templatePath);
            if (FileUtil.exist(finaPath)) {
                String md5Key = Md5.md5(new File(finaPath).getAbsolutePath());
                result.put("md5Key", md5Key);
            }
        }
    }

    private void handModels(Category category, Map<String, Object> result) {
        boolean isExtend = CommonUtils.BeNotBlank(category.getCategoryModelId());
        if (isExtend) {
            String designField = categoryModelService.getDesignField(category.getCategoryModelId());
            List<DynamicModel> dynamicModels = ModelFieldUtil.jsonStrToModel(category.getData());
            List<DynamicModel> designModels = ModelFieldUtil.jsonStrToModel(designField);
            ModelFieldUtil.copyField(designModels, dynamicModels);
            result.put("models", CommonUtils.BeNotEmpty(designModels) ? designModels : Lists.newArrayList());
        }
    }

    @Override
    public void genCategory(String id) {
        messageSend.sendMessage(ChannelEnum.CATEGORY, Channel.NOTIFY_IT, id, true);
    }


    @Override
    public Map<String, Object> loadTempParams(String categoryId) {
        Map<String, Object> mapDetail = categoryMapper.getMapDetail(categoryId, null, null);
        mapDetail.put(Channel.PAGE, false);
        mapDetail.put("contents", Lists.newArrayList());
        ModelFieldUtil.formatData(mapDetail);
        loadTempPath(mapDetail, categoryId);
        return mapDetail;
    }

    @Override
    public List<Category> listCategoryByPid(String categoryId) {
        List<Category> cmsCategoryDtos = categoryMapper.listCategoryByPid(categoryId);
        return CommonUtils.BeNotEmpty(cmsCategoryDtos) ? cmsCategoryDtos : Lists.newArrayList();
    }


    private void loadTempPath(Map<String, Object> mapDetail, String categoryId) {
        String templatePath = categoryTemplateService.getTempPathByCategoryId(categoryId);
        String sitePath = mileluConfig.getSitePath() + mileluConfig.getDomain();
        String destPath = mapDetail.get("path").toString();
        mapDetail.put(Channel.TEMP_PATH, templatePath);
        mapDetail.put(Channel.DEST_PATH, sitePath + destPath);
        mapDetail.put(Channel.DEST_PATH_COPY, sitePath + destPath);
        mapDetail.put(Channel.FRIST_PATH, mapDetail.get("path"));
    }

    @Override
    public Tree<Category> treeCategoryForContent() {
        List<Category> categoryDtos = categoryMapper.treeCategoryForContent();
        if (CommonUtils.BeNotEmpty(categoryDtos)) {
            List<Tree<Category>> trees = filterBuild(categoryDtos);
            return getCategoryTree(trees);
        }
        return null;
    }

    @Override
    public Category getDetailById(String categoryId) {
        Category categoryDto = categoryMapper.getDetail(categoryId);
        return categoryDto;
    }

    @Override
    public List<String> getIds() {
        List<String> ids = categoryMapper.getIds();
        return CommonUtils.BeNotEmpty(ids) ? ids : Lists.newArrayList();
    }

    @Override
    public List<CategoryNavbar> navbar(String code, Boolean showCount, Boolean showHideMenu) {
        boolean showHideItem = CommonUtils.BeNotNull(showHideMenu) && showHideMenu.booleanValue();
        List<CategoryNavbar> categorys = categoryMapper.navbar(showHideItem);
        if (CommonUtils.BeNotNull(showCount) && showCount) { //显示数量
            for (CategoryNavbar categoryNavbar : categorys) {
                Integer count = categoryMapper.countContent(null, categoryNavbar.getId());
                categoryNavbar.setCount(count);
            }
        }
        return build(categorys, code);
    }

    @Override
    public Map<String, Object> info(String code, String id) {
        Map<String, Object> params = new HashMap<>();
        Integer count = categoryMapper.countContent(code, id);
        Map<String, Object> categoryInfo = categoryMapper.getMapDetail(id, code, null);
        ModelFieldUtil.formatData(categoryInfo);
        categoryInfo.put("count", count);
        params.put("this", categoryInfo);
        if (CommonUtils.BeBlank(id)) id = categoryInfo.get("id").toString();
        List<Map<String, Object>> childs = childinfo(id); // 子分类
        params.put("childs", childs);
        String parentId = categoryInfo.get("parentId").toString();
        if (CommonUtils.BeNotBlank(parentId) && !"0".equals(parentId)) {
            Map<String, Object> parentInfo = categoryMapper.getMapDetail(parentId, null, null);
            params.put("parent", parentInfo);
        }
        return params;
    }
    /**
     * 获取面包屑
     * @param id
     * @param postfix
     * @param containit
     * @return
     */
    @Override
    public List<Category> breadCrumbs(String id, String postfix, Boolean containit) {
        List<Category> categoryDtos = new ArrayList<>();
        Category categoryDto = categoryMapper.getDetail(id);
        if(containit){
            categoryDtos.add(categoryDto);
        }
        if(!"0".equals(categoryDto.getParentId())){
            categoryLoop(categoryDto.getParentId(),postfix,categoryDtos);
        }
        return categoryDtos;
    }

    private void categoryLoop(String parentId, String postfix, List<Category> categoryDtos) {
        Category categoryDto = categoryMapper.getDetail(parentId);
        if(CommonUtils.BeNotNull(categoryDto)){
            categoryDto.setName(categoryDto.getName()+postfix);
            categoryDtos.add(categoryDto);
            if(!"0".equals(categoryDto.getParentId())){
                categoryLoop(categoryDto.getParentId(),postfix,categoryDtos);
            }
        }
    }

    private List<Map<String, Object>> childinfo(String id) {
        List<Map<String, Object>> categorysInfo = categoryMapper.getMapDetails(id);
        if (CommonUtils.BeNotEmpty(categorysInfo)) {
            for (Map<String, Object> category : categorysInfo) {
                ModelFieldUtil.formatData(category);
            }
        }
        return CommonUtils.BeNotEmpty(categorysInfo) ? categorysInfo : Lists.newArrayList();
    }

    private List<CategoryNavbar> build(List<CategoryNavbar> nodes, String code) {
        if (nodes == null) {
            return null;
        }
        List<CategoryNavbar> topNodes = new ArrayList<CategoryNavbar>();
        for (CategoryNavbar navbar : nodes) {
            formatJsonMap(navbar);
            String pid = navbar.getParentId();
            if (pid == null || "0".equals(pid)) {
                if (CommonUtils.BeNotBlank(code)) {
                    if (code.equals(navbar.getCode())) {
                        topNodes.add(navbar);
                        continue;
                    }
                } else {
                    topNodes.add(navbar);
                    continue;
                }
            }
            for (CategoryNavbar parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(navbar);
                    parent.setHasChildren(true);
                    continue;
                }
            }
        }
        return topNodes;

    }

    private void formatJsonMap(CategoryNavbar navbar) {
        if (CommonUtils.BeNotBlank(navbar.getData())) {
            Map param = new HashMap();
            param.put("data", navbar.getData());
            ModelFieldUtil.formatData(param);
            navbar.setExt(param);
        }
    }

    private Tree<Category> getCategoryTree(List<Tree<Category>> trees) {
        List<Tree<Category>> topNodes = BuildTree.buildList(trees, "0");
        Tree<Category> root = new Tree<Category>();
        root.setKey("0").setId("0").setParentId("0").setHasParent(false).setChildren(topNodes).setTitle("父分类");
        return root;
    }

    private List<Tree<Category>> filterBuild(List<Category> categoryDtos) {
        List<Tree<Category>> trees = new ArrayList<Tree<Category>>();
        for (Category categoryDto : categoryDtos) {
            Tree<Category> tree = new Tree<Category>();
            tree.setId(categoryDto.getId());
            tree.setKey(categoryDto.getId());
            tree.setParentId(categoryDto.getParentId());
            tree.setTitle(categoryDto.getName());
            tree.setSpread(true);
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("singlePage", categoryDto.getSinglePage());
            attributes.put("allowContribute", categoryDto.getAllowContribute());
            attributes.put("hidden", categoryDto.getHidden());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        return trees;
    }
}
