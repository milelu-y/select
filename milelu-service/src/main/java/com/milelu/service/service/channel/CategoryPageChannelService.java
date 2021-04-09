package com.milelu.service.service.channel;

import com.milelu.freemark.emums.ChannelEnum;
import com.milelu.freemark.processor.channel.BaseChannelAdaptService;
import com.milelu.service.service.content.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Java
 */
@Service
public class CategoryPageChannelService extends BaseChannelAdaptService implements Cloneable {

    @Autowired
    ContentService contentService;

    private Map<String, Object> params;

    private Boolean notify = false;

    @Override
    public void notifyIt(Map<String, Object> map) {
        this.params = map;
        execuate(true);
    }

    @Override
    public void notifyIt(Map<String, Object> map, Boolean notify) {
        this.params = map;
        if (notify) {
            this.notify = true;
            execuate(true, true);
        } else {
            execuate(true);
        }
    }

    @Override
    public void callBack(Object o) {
        System.out.println(o);
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
        Map<String,Object> params = contentService.listContent(this.params,notify);
        setPath(params,false);
        return params;
    }

    @Override
    public ChannelEnum getName() {
        return ChannelEnum.CATEGORY_PAGE;
    }
}
