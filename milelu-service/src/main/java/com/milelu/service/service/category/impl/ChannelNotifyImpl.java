package com.milelu.service.service.category.impl;

import com.milelu.common.constant.Channel;
import com.milelu.common.utils.CommonUtils;
import com.milelu.freemark.emums.ChannelEnum;
import com.milelu.freemark.processor.message.MessageSend;
import com.milelu.service.service.category.CategoryService;
import com.milelu.service.service.category.ChannelNotifyService;
import com.milelu.service.service.content.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author MILELU
 * @date 2021/1/15 15:32
 */
@Service
public class ChannelNotifyImpl implements ChannelNotifyService {

    @Autowired
    private MessageSend messageSend;

    @Autowired
    private ContentService contentService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void notifyHomePage() {
        messageSend.sendMessage(ChannelEnum.HOME, Channel.NOTIFY_IT,true);
    }

    @Override
    public void notifyWholeSite() {
        notifyHomePage();
        notifyCategoey();
        notifyContent(1,1000);
    }

    @Override
    public void notifyCategory() {
        notifyCategoey();
    }

    private void notifyContent(Integer pageNo,Integer pageSize){
        Map<String,Object> result = contentService.pageContentParamsForAllGen(pageNo,pageSize);
        Boolean hasNext = (Boolean) result.get("hasNext") ;
        long total = (Long) result.get("total") ;
        List<Map<String,String >> rows = (List<Map<String,String >>) result.get("rows");
        if(CommonUtils.BeNotEmpty(rows)){
            messageSend.sendMessage(ChannelEnum.CONTENTS, Channel.NOTIFY_IT,rows,total,pageNo,pageSize);
        }
        if(hasNext){
            pageNo++;
            notifyContent(pageNo,pageSize);
        }
    }

    private void notifyCategoey(){
        List<String> ids =categoryService.getIds();
        if(CommonUtils.BeNotEmpty(ids)){
            for(String id:ids){
                messageSend.sendMessage(ChannelEnum.CATEGORY, Channel.NOTIFY_IT,id,true);
            }
        }
    }
}
