package com.milelu.service.service.channel;

import com.milelu.common.constant.Channel;
import com.milelu.freemark.emums.ChannelEnum;
import com.milelu.freemark.processor.channel.BaseChannelAdaptService;
import com.milelu.freemark.processor.message.MessageSend;
import com.milelu.service.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author MILELU
 * @date 2021/1/15 15:13
 */
@Service
public class CategoryChannelService extends BaseChannelAdaptService implements Cloneable {

    @Autowired
    CategoryService categoryService;

    @Autowired
    MessageSend messageSend;

    private String categoryId;

    private Boolean notify = false;

    @Override
    public void notifyIt(String categoryId) {
        this.categoryId = categoryId;
        execuate(true);
    }

    @Override
    public void notifyIt(String categoryId, Boolean notify) {
        this.categoryId = categoryId;
        if (notify) {
            this.notify = true;
            execuate(true, true);
        } else {
            execuate(true);
        }
    }

    @Override
    public void callBack(Object param) {
        if (param instanceof Map) {
            Map params = (Map) param;
            Boolean allow = (Boolean) params.get("allowContribute");
            Boolean onlyUrl = (Boolean) params.get("onlyUrl");
            Integer pageSize = (Integer) params.get("pageSize");
            if (allow && !onlyUrl && pageSize > 0) {
                params.put(Channel.PAGE, true);
                if (notify) {
                    messageSend.sendMessage(ChannelEnum.CATEGORY_PAGE, Channel.NOTIFY_IT, params, true);
                } else {
                    messageSend.sendMessage(ChannelEnum.CATEGORY_PAGE, Channel.NOTIFY_IT, params);
                }
            } else {
                params.put(Channel.PAGE, false);
            }
        }
    }

    @Override
    protected String loadTempPath() {
        return super.tempPath;
    }

    @Override
    protected String loadDestPath() {
        return super.destPath;
    }

    @Override
    protected Map<String, Object> loadTempParams(Map<String, Object> variable) {
        Map<String, Object> params = categoryService.loadTempParams(categoryId);
        setPath(params, false);
        return params;
    }


    @Override
    public ChannelEnum getName() {
        return ChannelEnum.CATEGORY;
    }
}
