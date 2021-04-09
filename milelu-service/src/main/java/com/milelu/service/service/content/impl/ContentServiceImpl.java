package com.milelu.service.service.content.impl;

import java.math.BigDecimal;
import java.util.*;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.milelu.common.config.MileluConfig;
import com.milelu.common.constant.Channel;
import com.milelu.common.constant.Constants;
import com.milelu.common.core.redis.RedisCache;
import com.milelu.common.enums.FilterField;
import com.milelu.common.enums.InputTypeEnum;
import com.milelu.common.enums.PathRule;
import com.milelu.common.utils.*;
import com.milelu.common.utils.model.DynamicModel;
import com.milelu.common.utils.model.Tree;
import com.milelu.freemark.emums.ChannelEnum;
import com.milelu.freemark.emums.StatusEnum;
import com.milelu.freemark.processor.message.MessageSend;
import com.milelu.service.domain.*;
import com.milelu.service.service.category.CategoryModelRelationService;
import com.milelu.service.service.category.CategoryService;
import com.milelu.service.service.content.ContentAttachService;
import com.milelu.service.service.content.ContentAttributeService;
import com.milelu.service.service.content.ContentService;
import com.milelu.service.service.model.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.milelu.service.mapper.ContentMapper;
import org.springframework.transaction.annotation.Transactional;

import static com.milelu.common.utils.uuid.SnowflakeIdWorker.getId;

/**
 * 内容Service业务层处理
 *
 * @author MILELU
 * @date 2021-01-23
 */
@Slf4j
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    ModelService modelService;

    @Autowired
    ContentAttachService contentAttachService;

    @Autowired
    MessageSend messageSend;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ContentAttributeService contentAttributeService;

    @Autowired
    private MileluConfig mileluConfig;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    CategoryModelRelationService modelRelationService;

    /**
     * 查询内容
     *
     * @param id 内容ID
     * @return 内容
     */
    @Override
    public Content selectContentById(String id) {
        return contentMapper.selectContentById(id);
    }

    /**
     * 查询内容列表
     *
     * @param content 内容
     * @return 内容
     */
    @Override
    public List<Content> selectContentList(Content content) {
        return contentMapper.selectContentList(content);
    }

    /**
     * 新增内容
     *
     * @param content 内容
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertContent(Content content) {
        content.setStatus("0").setId(getId()).setHasFiles(false)
                .setCreateTime(DateUtils.getNowDate());
        insertUpdateAttr(content, true);
        Content v = formatIt(content, true);
        contentMapper.insertContent(v);
        //TODO:发布内容
        messageSend.sendMessage(ChannelEnum.CONTENT, Channel.NOTIFY_IT, buildParams(content));
        messageSend.sendMessage(ChannelEnum.HOME, Channel.NOTIFY_IT);
    }

    private Object buildParams(Content content) {
        Map<String, Object> map = new HashMap<>(8);
        map.put(Channel.CATEGORY_ID, content.getCategoryId());
        map.put(Channel.MODEL_ID, content.getModelId());
        map.put(Channel.CONTENT_ID, content.getId());
        return map;
    }

    private void insertUpdateAttr(Content v, boolean isSave) {
        List<DynamicModel> extendFields = findExtendField(v.getModelId(), v.getParams());
        DynamicModel attachField = checkHasField(extendFields, InputTypeEnum.INPUT_ATTACH, true);
        if (CommonUtils.BeNotNull(attachField)) {
            insertAttach(attachField, v.getId(), isSave);
            v.setHasFiles(true);
        }
        DynamicModel tagField = checkHasField(extendFields, InputTypeEnum.INPUT_TAG, true);
        if (CommonUtils.BeNotNull(tagField)) {
            //TODO:标签
//            insertTags(tagField,v);
            v.setHasTags(true);
        }
        if (isSave) {
            insertAttr(v, extendFields);
        } else {
            updateAttr(v, extendFields);
        }
    }

    private void updateAttr(Content v, List<DynamicModel> extendFields) {
        ContentAttribute contentAttrDto = contentAttributeService.getByField("content_id", v.getId());
        contentAttrDto.setText(v.getText()).setOrigin(v.getOrigin()).setOriginUrl(v.getOriginUrl());
        if (CommonUtils.BeNotEmpty(extendFields)) {
            contentAttrDto.setData(ModelFieldUtil.modelToJsonStr(extendFields));
        }
        contentAttributeService.updateContentAttribute(contentAttrDto);
    }

    private void insertAttr(Content v, List<DynamicModel> extendFields) {
        ContentAttribute contentAttrDto = new ContentAttribute();
        contentAttrDto.setContentId(v.getId()).setId(getId())
                .setText(v.getText()).setOrigin(v.getOrigin()).setOriginUrl(v.getOriginUrl());
        if (CommonUtils.BeNotEmpty(extendFields)) {
            contentAttrDto.setData(ModelFieldUtil.modelToJsonStr(extendFields));
        }
        contentAttributeService.insertContentAttribute(contentAttrDto);
    }

    private void insertAttach(DynamicModel attachModel, String contentId, boolean isSave) {
        if (CommonUtils.BeNotNull(attachModel) && CommonUtils.BeNotNull(attachModel.getDefaultValue())) {
            List<Map> attachs = (List<Map>) attachModel.getDefaultValue();
            if (!isSave) {
                contentAttachService.deleteByFiled("content_id", contentId);
            }
            List<ContentAttach> contentAttachDtos = buildAttach(attachs, contentId);
            for (ContentAttach contentAttachDto : contentAttachDtos) {
                contentAttachService.insertContentAttach(contentAttachDto);
            }
        }
    }

    private DynamicModel checkHasField(List<DynamicModel> extendFields, InputTypeEnum inputTypeEnum, boolean remove) {
        DynamicModel attachModel = null;
        if (CommonUtils.BeNotEmpty(extendFields)) {
            Iterator<DynamicModel> iterator = extendFields.iterator();
            while (iterator.hasNext()) {
                DynamicModel model = iterator.next();
                boolean hasAttach = inputTypeEnum.getCode().equals(model.getInputType())
                        && inputTypeEnum.getCode().equals(model.getFieldCode());
                if (hasAttach) {
                    attachModel = model;
                    if (remove) {
                        iterator.remove();
                    }
                }
            }
        }
        return attachModel;
    }

    private List<DynamicModel> findExtendField(String modelId, Map<String, Object> params) {
        Model model = modelService.getModel(modelId);
        String fieldStr = model.getAllFieldList();
        Boolean hasFile = model.getHasFiles();
        List<DynamicModel> extendField = new ArrayList<>();
        if (hasFile) {
            List<DynamicModel> attachField = ModelFieldUtil.loadModel(FilterField.ATTACH);
            if (CommonUtils.BeNotEmpty(attachField)) {
                extendField.addAll(attachField);
            }
        }
        if (CommonUtils.BeNotNull(params) && !params.isEmpty()) {
            List<DynamicModel> exdfields = ModelFieldUtil.listExtendModel(fieldStr);
            if (CommonUtils.BeNotEmpty(exdfields)) {
                extendField.addAll(exdfields);
            }
        }
        ModelFieldUtil.filterMapToSetFieldValue(extendField, params);
        return extendField;
    }

    private Content formatIt(Content v, boolean isSave) {
        Category categoryDto = categoryService.getDetailById(v.getCategoryId());
        if (isSave) {
            String destPath = PathRule.getPathRule(categoryDto.getPathRule()).format(categoryDto.getCode(), v.getId()) +
                    Constants.DEFAULT_HTML_SUFFIX;
            v.setUrl(destPath).setPathRule(categoryDto.getPathRule());
        } else {
            String pathRule = contentMapper.getPathRule(v.getId());
            boolean hasChange = categoryDto.getPathRule().equals(pathRule);
            if (!hasChange) {
                String destPath = PathRule.getPathRule(categoryDto.getPathRule()).format(categoryDto.getCode(), v.getId()) +
                        Constants.DEFAULT_HTML_SUFFIX;
                v.setUrl(destPath).setPathRule(categoryDto.getPathRule());
            }
        }
        v.setCreateId(SecurityUtils.getUserId());
        String pcategoryId = categoryDto.getContainChild() ? categoryDto.getParentId() : "";
        v.setPCategoryId(pcategoryId);
        return v;
    }

    private List<ContentAttach> buildAttach(List<Map> attachs, String contentId) {
        List<ContentAttach> attachDtos = new ArrayList<>();
        if (CommonUtils.BeNotEmpty(attachs)) {
            for (Map attachMap : attachs) {
                ContentAttach attachDto = new ContentAttach();
                ModelFieldUtil.setFieldVal(attachDto, attachMap);
                attachDto.setContentId(contentId).setData(JSONUtil.toJsonStr(attachMap));
                attachDtos.add(attachDto);
            }
        }
        return attachDtos;
    }

    /**
     * 修改内容
     *
     * @param content 内容
     * @return 结果
     */
    @Override
    public void updateContent(Content content) {
        content.setUpdateTime(DateUtils.getNowDate());
        insertUpdateAttr(content, false);
        Content v = formatIt(content, false);
        contentMapper.updateContent(v);
        messageSend.sendMessage(ChannelEnum.CONTENT, Channel.NOTIFY_IT, buildParams(content));
        if (StatusEnum.PUHLISH.getCode().equals(content.getStatus())) {
            messageSend.sendMessage(ChannelEnum.HOME, Channel.NOTIFY_IT);
            messageSend.sendMessage(ChannelEnum.CATEGORY, Channel.NOTIFY_IT, v.getCategoryId());
        }

    }

    /**
     * 批量删除内容
     *
     * @param ids 需要删除的内容ID
     * @return 结果
     */
    @Override
    public void deleteContentByIds(String[] ids, boolean realDel) {
        if (CommonUtils.BeNotEmpty(ids)) {
            for (String id : ids) {
                delete(id, realDel);
            }
        }
    }

    private void delete(String id, boolean realDel) {
        if (realDel) {
            delAboutContet(id);
        } else {
            contentMapper.updateStatus(id, StatusEnum.DELETE.getCode());
        }
        String categoryId = contentMapper.getCategoryId(id);
        messageSend.sendMessage(ChannelEnum.HOME, Channel.NOTIFY_IT);
        if (CommonUtils.BeNotBlank(categoryId)) {
            messageSend.sendMessage(ChannelEnum.CATEGORY, Channel.NOTIFY_IT, categoryId);
        }
    }

    private void delAboutContet(String contentId) {
        contentAttributeService.deleteByFiled("content_id", contentId);
        contentAttachService.deleteByFiled("content_id", contentId);
        //TODO：//删除推荐位数据
//        relatedService.deleteByFiled("content_id",contentId);
        contentMapper.deleteContentById(contentId);
    }

    /**
     * 删除内容信息
     *
     * @param id 内容ID
     * @return 结果
     */
    @Override
    public int deleteContentById(String id) {
        return contentMapper.deleteContentById(id);
    }

    /**
     * 查询内容列表
     *
     * @param content
     * @return
     */
    @Override
    public List<Content> selectContentLists(Content content) {
        filterCategory(content);
        List<Content> contents = contentMapper.selectContentLists(content);
        //TODO:处理图片
//        if(CommonUtils.BeNotEmpty(contents)) {
//            filterCover(contents);
//        }
        return contents;
    }


//    private void filterCover(List<Content> contents) {
//        for(Content content:contents){
//            if(CommonUtils.BeNotBlank(content.getCoverStr())){
//                content.setCover(JSONUtil.toList(JSONUtil.parseArray(content.getCoverStr()), Map.class));
//            }
//        }
//    }

    private void filterCategory(Content content) {
        if (CommonUtils.BeNotBlank(content.getCategoryId())) {
            List<String> childcategoryIds = new ArrayList<>();
            childcategoryIds.add(content.getCategoryId());
            List<String> newChildCategoryIds = new ArrayList<>();
            recursionCategoryIds(childcategoryIds, newChildCategoryIds);
            content.setCategoryIds(newChildCategoryIds);
        }
    }

    private void recursionCategoryIds(List<String> childcategoryIds, List<String> newChildCategoryIds) {
        if (CommonUtils.BeNotEmpty(childcategoryIds)) {
            newChildCategoryIds.addAll(childcategoryIds);
            for (String categoryId : childcategoryIds) {
                if (CommonUtils.BeNotBlank(categoryId)) {
                    List<String> childCategs = getCategoryChildByParentId(categoryId);
                    recursionCategoryIds(childCategs, newChildCategoryIds);
                }
            }
        }
    }

    private List<String> getCategoryChildByParentId(String categoryId) {
        List<String> categoryIds = new ArrayList<>();
        List<Category> cmsCategoryDtos = categoryService.listCategoryByPid(categoryId);
        if (CommonUtils.BeNotEmpty(cmsCategoryDtos)) {
            for (Category cmsCategoryDto : cmsCategoryDtos) {
                categoryIds.add(cmsCategoryDto.getId());
            }
        }
        return categoryIds;
    }

    @Override
    public Tree<Category> treeCategory() {
        return categoryService.treeCategoryForContent();
    }


    @Override
    public List<DynamicModel> getFormDesign(String modelId) {
        Model model = modelService.selectModelById(modelId);
        Boolean hasFiles = model.getHasFiles();
        List<DynamicModel> dynamicModels = ModelFieldUtil.jsonStrToModel(model.getAllFieldList());
        if (hasFiles) {
            List<DynamicModel> attachField = ModelFieldUtil.loadModel(FilterField.ATTACH);
            if (CommonUtils.BeNotEmpty(attachField)) {
                dynamicModels.addAll(attachField);
            }
        }
        return dynamicModels;
    }

    @Override
    public Map<String, Object> loadTempParams(Map<String, Object> params, List<String> status) {
        String destPath = mileluConfig.getDomain();
        String templatePath = modelRelationService.loadTemplatePath(params);
        String contentId = params.get(Channel.CONTENT_ID).toString();
        Map<String, Object> map = contentMapper.loadTempParams(contentId, status);
//        setTags(map);
//        setAttach(map,contentId);
        ModelFieldUtil.formatCover(map, true);
        ModelFieldUtil.formatData(map);
        map.put(Channel.DEST_PATH, destPath);
        map.put(Channel.TEMP_PATH, templatePath);
        return map;
    }

    @Override
    public Map<String, Object> getDetail(String id) {
        Map<String, Object> map = new HashMap<>();
        List<DynamicModel> dynamicModels = new ArrayList<>();
        Content content = contentMapper.selectContentById(id);
        if (CommonUtils.BeNotNull(content)) {
            dynamicModels = getFormDesign(content.getModelId());
            ContentAttribute attrDto = contentAttributeService.getByField("content_id", content.getId());
            filterModelField(dynamicModels, content, attrDto);
            map.put("content", content);
        }
        map.put("models", dynamicModels);
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publish(PublishDto publishDto, boolean byJob) {
        List<String> ids = publishDto.getIds();
        Set<String> categoryIds = listCategoryByCids(ids);
        String status = publishDto.getStatus();
        if (Arrays.asList(StatusEnum.DRAFT.getCode(), StatusEnum.PUHLISH.getCode()).contains(status)) {
            Integer count = contentMapper.publish(ids, status, publishDto.getDate(), byJob);
            if (count > 0) {
                previousGen(ids);
                messageSend.sendMessage(ChannelEnum.HOME, Channel.NOTIFY_IT);
                if (!categoryIds.isEmpty()) {
                    categoryIds.forEach(categoryId -> {
                        messageSend.sendMessage(ChannelEnum.CATEGORY, Channel.NOTIFY_IT, categoryId);
                    });
                }
            }
        }
    }


    /**
     * 查找当前文章的上一篇文章再次生成 上一篇 下一篇
     *
     * @param ids
     * @return
     */
    private void previousGen(List<String> ids) {
        if (CommonUtils.BeNotEmpty(ids)) {
            for (String id : ids) {
                String categoryId = contentMapper.getCategoryId(id);
                Map<String, Object> previous = contentMapper.nextPrevious(id, false, categoryId);
                Map<String, Object> next = contentMapper.nextPrevious(id, true, categoryId);
                notifyMsg(previous);
                notifyMsg(next);
            }
        }
    }

    private void notifyMsg(Map<String, Object> nextPrevious) {
        if (CommonUtils.BeNotNull(nextPrevious)) {
            Map<String, Object> map = new HashMap<>();
            map.put(Channel.CATEGORY_ID, nextPrevious.get("categoryId"));
            map.put(Channel.MODEL_ID, nextPrevious.get("modelId"));
            map.put(Channel.CONTENT_ID, nextPrevious.get("id"));
            messageSend.sendMessage(ChannelEnum.CONTENT, Channel.NOTIFY_IT, map);
        }
    }

    private Set<String> listCategoryByCids(List<String> ids) {
        Set<String> sets = new HashSet<>();
        if (CommonUtils.BeNotEmpty(ids)) {
            List<String> categoryIds = contentMapper.listCategoryByCids(ids);
            if (CommonUtils.BeNotEmpty(categoryIds)) {
                sets = new HashSet<>(categoryIds);
            }
        }
        return sets;
    }

    private void filterModelField(List<DynamicModel> dynamicModels, Content content, ContentAttribute attr) {
        if (CommonUtils.BeNotBlank(attr.getText())) {
            content.setText(attr.getText());
        }
        if (CommonUtils.BeNotBlank(attr.getOrigin())) {
            content.setOrigin(attr.getOrigin());
        }
        if (CommonUtils.BeNotBlank(attr.getOriginUrl())) {
            content.setOriginUrl(attr.getOriginUrl());
        }
        if (CommonUtils.BeNotEmpty(dynamicModels)) {
            ModelFieldUtil.setFieldVal(dynamicModels, content, true);
        }
        if (CommonUtils.BeNotBlank(attr.getData())) {
            List<DynamicModel> extendFields = ModelFieldUtil.jsonStrToModel(attr.getData());
            if (CommonUtils.BeNotEmpty(extendFields)) {
                ModelFieldUtil.copyField(dynamicModels, extendFields);
            }
        }
        DynamicModel attachField = checkHasField(dynamicModels, InputTypeEnum.INPUT_ATTACH, false);
        if (CommonUtils.BeNotNull(attachField)) {
            List<String> datas = contentAttachService.listData(content.getId());
            if (CommonUtils.BeNotEmpty(datas)) {
                List<Map> value = new ArrayList<>();
                for (String data : datas) {
                    value.add(JSONUtil.toBean(data, Map.class));
                }
                attachField.setDefaultValue(value);
            }
        }
        //TODO:tag标签
//        DynamicModel tagField = checkHasField(dynamicModels,InputTypeEnum.INPUT_TAG,false);
//        if(CommonUtils.BeNotNull(tagField) && CommonUtils.BeNotBlank(content.getTagIds())){
//            List<String> tags=tagService.listTags(Arrays.asList(content.getTagIds().split(",")));
//            if(CommonUtils.BeNotEmpty(tags)){
//                tagField.setDefaultValue(tags);
//            }
//        }
    }

    @Override
    public Map<String, Object> listContent(Map<String, Object> map, Boolean notify) {
        pageIt(map, notify);
        return map;
    }

    @Override
    public void reStaticBatchGenCid(List<String> ids) {
        if (CommonUtils.BeNotEmpty(ids)) {
            List<Map<String, Object>> maps = contentMapper.buildParams(ids);
            if (CommonUtils.BeNotEmpty(maps)) {
                maps.forEach(map -> {
                    messageSend.sendMessage(ChannelEnum.CONTENT, Channel.NOTIFY_IT, map, true);
                });
            }
        }
    }

    @Override
    public void jobPublish(List<String> ids, Date date) {
        contentMapper.jobPublish(ids, date);
    }

    @Override
    public Map<String, Object> pageContentParamsForAllGen(Integer pageNo, Integer pageSize) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, String>> rows = new ArrayList<>();
        Boolean hasNext = pageContent(pageNo, pageSize, rows, result);
        result.put("rows", rows);
        result.put("hasNext", hasNext);
        return result;
    }

    /**
     * 获取指定栏目编码的内容
     *
     * @param code
     * @param num
     * @param order
     * @return
     */
    @Override
    public List<Map<String, Object>> listByCode(String code, String categoryId, Integer num, String order) {
        List<String> asc = new ArrayList<>();
        List<String> desc = new ArrayList<>();
        if (CommonUtils.BeNotBlank(order)) {
            String[] orderFields = order.split(",");
            for (String orderField : orderFields) {
                String[] arr = orderField.split("\\s+");
                if (arr[1].toLowerCase().equals("desc")) {
                    desc.add(arr[0]);
                } else {
                    asc.add(arr[0]);
                }
            }
        }
        List<Map<String, Object>> contents = contentMapper.listByCode(code, categoryId, num, asc, desc);
        formatContent(contents);
        //TODO:标签
//        setTags(contents);
        setAttachs(contents);
        return contents;
    }

    @Override
    public Map<String, Object> nextPrevious(String id, String categoryId) {
        Map<String, Object> content = new HashMap<>();
        Map<String, Object> next = contentMapper.nextPrevious(id, true, categoryId);
        Map<String, Object> previous = contentMapper.nextPrevious(id, false, categoryId);
        content.put("next", next);
        content.put("previous", previous);
        return content;
    }

    /**
     * 获取最新内容
     *
     * @param categoryId
     * @param code
     * @param where
     * @return
     */
    @Override
    public Map<String, List> getTopNews(String categoryId, String code, String where) {
        Map<String, List> params = new HashMap<>();
        if (CommonUtils.BeNotBlank(where)) {
            String[] conditions = where.split(",");
            if (!where.contains(",")) {
                conditions = new String[]{where};
            }
            for (String condition : conditions) {
                String[] carr = condition.split("\\s+");
                String field = carr[0];
                Integer num = carr.length == 2 ? Integer.valueOf(carr[1]) : 10;
                List<String> codes = new ArrayList<>();
                if (CommonUtils.BeNotBlank(code)) {
                    codes = Arrays.asList(code.trim().split(","));
                }
                List<Map<String, Object>> contents = contentMapper.getTopNews(categoryId, codes, field, num);
                ModelFieldUtil.formatDatas(contents);
                ModelFieldUtil.formatCoverObjs(contents, true);
                params.put(field, CommonUtils.BeNotEmpty(contents) ? contents : Lists.newArrayList());
            }
        }
        return params;
    }

    @Override
    public void top(Content content) {
//        if(CommonUtils.BeNotEmpty(content.getTopTags())){
//            content.setTopTag(StringUtils.join(content.getTopTags(),","));
//        }
        contentMapper.top(content);
        messageSend.sendMessage(ChannelEnum.HOME, Channel.NOTIFY_IT);
    }

    @Override
    public PageBean<Content> questionList(int start, int end, String title) {
        PageBean<Content> pageInfos = new PageBean<>();
        //1.设置limit
        Integer limit = end;
        pageInfos.setLimit(limit);
        //2.设置总记录数
        Integer total = contentMapper.count();
        pageInfos.setTotal(total);
        //3.设置总的页数
        Integer totalPage;
        if (total % limit == 0) {
            totalPage = total / limit;
        } else {
            totalPage = total / limit + 1;
        }
        pageInfos.setTotalPage(totalPage);
        //4.设置页数的集合
        List<Integer> pages = new ArrayList<>();
        for (int i = 1; i < totalPage + 1; i++) {
            pages.add(i);
        }
        pageInfos.setPages(pages);
        pageInfos.setPage(start);
        //5.设置每页的数据集合
        List<Content> questions = contentMapper.questionList(start, limit, title);
        pageInfos.setPageRecode(questions);
        return pageInfos;
    }

    private void setAttachs(List<Map<String, Object>> contents) {
        if (CommonUtils.BeNotEmpty(contents)) {
            for (Map<String, Object> content : contents) {
                String id = content.get("id").toString();
                setAttach(content, id);
            }
        }
    }

    private void setAttach(Map<String, Object> map, String id) {
        List<Map> attach = contentAttachService.listAttach(id);
        map.put(Channel.ATTACH, CommonUtils.BeNotEmpty(attach) ? attach : Lists.newArrayList());
    }

    private Boolean pageContent(Integer pageNo, Integer pageSize, List<Map<String, String>> rows, Map<String, Object> params) {
        IPage<Map<String, String>> pages = new Page<>(pageNo, pageSize);
        PageHelper.startPage(pageNo, pageSize);
        com.github.pagehelper.Page<Map<String, String>> result = contentMapper.pageAllContentForGen(pages);
        params.put("total", result.getTotal());
        if (pages.getCurrent() <= result.getPages() && CommonUtils.BeNotEmpty(result.getResult())) {
            String destPath = mileluConfig.getDomain();
            List<String> cids = new ArrayList<>();
            for (Map map : result.getResult()) {
                String contentId = map.get(Channel.CONTENT_ID).toString();
                if (!cids.contains(contentId)) {
                    cids.add(contentId);
                }
            }
            Map<String, Object> attachs = getAttachs(cids);
            for (Map map : result.getResult()) {
                String contentId = map.get(Channel.CONTENT_ID).toString();
                String templatePath = modelRelationService.loadTemplatePath(map);
                setAttachs(map, attachs, contentId);
                ModelFieldUtil.formatCover(map, true);
                ModelFieldUtil.formatData(map);
                map.put(Channel.DEST_PATH, destPath);
                map.put(Channel.TEMP_PATH, templatePath);
            }
            rows.addAll(result.getResult());
        }
        if (pageNo == result.getPages() || result.getPages() == 0) {
            return false;
        }
        return true;
    }

    private void setAttachs(Map map, Map<String, Object> attachs, String contentId) {
        Object attach = attachs.get(contentId);
        map.put(Channel.ATTACH, CommonUtils.BeNotNull(attach) ? attach : Lists.newArrayList());
    }

    private Map<String, Object> getAttachs(List<String> contentIds) {
        Map<String, Object> maps = new HashMap();
        List<Map> attachs = contentAttachService.listAttachs(contentIds);
        if (CommonUtils.BeNotEmpty(attachs)) {
            for (Map attach : attachs) {
                if (maps.containsKey(attach.get(Channel.CONTENT_ID))) {
                    List<Map> attachList = (List<Map>) maps.get(Channel.CONTENT_ID);
                    attachList.add(attach);
                } else {
                    List<Map> list = new ArrayList<>();
                    list.add(attach);
                    maps.put(attach.get(Channel.CONTENT_ID).toString(), attach);
                }
            }
        }
        return maps;
    }

    private void pageIt(Map<String, Object> objectMap, Boolean notify) {
        String categoryId = objectMap.get("id").toString();
        Integer pageSize = (Integer) objectMap.get("pageSize");
        Integer pageNo = CommonUtils.BeNull(objectMap.get("pageNo")) ? 1 : (Integer) objectMap.get("pageNo");
        Integer topPages = CommonUtils.BeNull(objectMap.get("topPages")) ? 5 : (Integer) objectMap.get("topPages");
        try {
            pageDatas(categoryId, pageSize, pageNo, topPages, objectMap, notify);
        } catch (Exception e) {
            redisCache.deleteObject(Channel.CACHE_CONTENT + categoryId);
            log.error(e.getMessage());
            throw e;
        }
    }

    private IPage<Map<String, Object>> pageDatas(String categoryId, Integer pageSize, Integer pageNo, Integer topPages, Map<String, Object> params, Boolean notify) {
        IPage<Map<String, Object>> result = cachePageDate(categoryId, pageSize, pageNo, params);
        setPage(result, params);
        setDestPath(params, pageNo);
        if (result.getCurrent() != result.getPages() && result.getTotal() > 0) {
            Map<String, Object> tempParams = new HashMap<>();
            tempParams.putAll(params);
            tempParams.remove(Channel.CONTENTS);
            if (result.getTotal() > 50000) {
                if (pageNo.intValue() < topPages.intValue()) {
                    messageSend.sendMessage(ChannelEnum.CATEGORY_PAGE, Channel.NOTIFY_IT, tempParams, notify);
                }
            } else {
                messageSend.sendMessage(ChannelEnum.CATEGORY_PAGE, Channel.NOTIFY_IT, tempParams, notify);
            }
        } else {
            String key = Channel.CACHE_CONTENT + categoryId;
            redisCache.deleteObject(key);
        }
        return result;
    }

    private void setDestPath(Map<String, Object> params, Integer pageNo) {
        String fristPath = params.get(Channel.FRIST_PATH).toString();
        String pathPrefix = fristPath.replace(Constants.DEFAULT_HTML_SUFFIX, "");
        if (pageNo > 1) {
            String destPathCopy = params.get(Channel.DEST_PATH_COPY).toString();
            String despath = destPathCopy.replace(Constants.DEFAULT_HTML_SUFFIX, "") + "_" + pageNo + Constants.DEFAULT_HTML_SUFFIX;
            String path = pathPrefix + "_" + pageNo + Constants.DEFAULT_HTML_SUFFIX;
            params.put(Channel.DEST_PATH, despath);
            params.put(Channel.PATH, path);
        }
        params.put(Channel.PATH_PREFIX, pathPrefix);
    }

    private void setPage(IPage<Map<String, Object>> result, Map<String, Object> objectMap) {
        objectMap.put("pageNo", Long.valueOf(result.getCurrent()).intValue() + 1);
        objectMap.put("pageSize", Long.valueOf(result.getSize()).intValue());
        objectMap.put("total", result.getTotal());
        objectMap.put("totalPage", result.getPages());
        objectMap.put("hasNext", result.getCurrent() != result.getPages());
        objectMap.put("contents", formatContent(result.getRecords()));
        if (result.getTotal() != 0) {
            double percent = new BigDecimal(result.getCurrent()).
                    divide(new BigDecimal(result.getPages()), 2, BigDecimal.ROUND_HALF_UP).
                    multiply(new BigDecimal(100)).doubleValue();
            objectMap.put(Channel.PERCENT, percent);
        }
    }

    private IPage<Map<String, Object>> cachePageDate(String categoryId, Integer pageSize, Integer pageNo, Map<String, Object> params) {

        IPage<Map<String, Object>> resultPage = new Page<>(pageNo, pageSize);
        long maxRow = calculSuitable(1000, pageSize);
        int multiple = limitMultiple(maxRow, Long.valueOf(pageSize));
        String key = Channel.CACHE_CONTENT + categoryId;
        Boolean hasKey = redisCache.hasKey(key);
        if (!hasKey) {
            int startNo = pageNo / multiple + 1;
            IPage<Map<String, Object>> pages = new Page<>(startNo, maxRow);
            PageHelper.startPage(startNo, (int) maxRow);
            com.github.pagehelper.Page<Map<String, Object>> result = contentMapper.pageContent(pages, categoryId);
            System.out.println(result);
            if (result.getTotal() > 0 && CommonUtils.BeNotEmpty(result.getResult())) {
                redisCache.rpList(key, result.getResult());
            }
            resultPage.setTotal(result.getTotal()).setCurrent(pageNo);
            if (result.getTotal() % pageSize != 0) {
                resultPage.setPages(result.getTotal() / pageSize + 1);
            } else {
                resultPage.setPages(result.getTotal() / pageSize);
            }
            if (CommonUtils.BeNotEmpty(result.getResult())) {
                int end = result.getResult().size() > pageSize ? pageSize : result.getResult().size();
                resultPage.setRecords(result.getResult().subList(0, end));
            }
        } else {
            int startNo = pageNo % multiple;
            if (pageNo > 1 && startNo == 1) {
                redisCache.deleteObject(key);
                return cachePageDate(categoryId, pageSize, pageNo, params);
            } else {// from redis
                int indexNo = 0;
                Long mapTotalPage = (Long) params.get("totalPage");
                Long mapTotal = (Long) params.get("total");
                if (startNo != 0) {
                    indexNo = (startNo - 1) * pageSize;
                } else {
                    indexNo = (multiple - 1) * pageSize;
                }
                List<Map<String, Object>> datas = redisCache.range(key, indexNo, indexNo + pageSize - 1);
                resultPage.setRecords(datas).setPages(mapTotalPage).setTotal(mapTotal);
            }
        }
        return resultPage;
    }

    private int limitMultiple(Long maxRowRead, Long pageSize) {
        Long multiple = (maxRowRead / pageSize);
        return multiple.intValue();
    }

    private long calculSuitable(long maxRow, long pageSize) {
        long num = maxRow / pageSize;
        return num * pageSize;
    }

    private List<Map<String, Object>> formatContent(List<Map<String, Object>> contents) {
        if (CommonUtils.BeNotEmpty(contents)) {
            for (Map map : contents) {
                ModelFieldUtil.formatData(map);
                ModelFieldUtil.formatCover(map, true);
            }
            return contents;
        }
        return Lists.newArrayList();
    }
}
