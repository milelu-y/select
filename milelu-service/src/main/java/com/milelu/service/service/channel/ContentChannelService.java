package com.milelu.service.service.channel;
import com.milelu.common.config.MileluConfig;
import com.milelu.common.constant.Channel;
import com.milelu.common.utils.CommonUtils;
import com.milelu.freemark.emums.ChannelEnum;
import com.milelu.freemark.processor.channel.BaseChannelAdaptService;
import com.milelu.freemark.processor.channel.ChannelThreadLocal;
import com.milelu.service.service.content.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Java
 */
@Service
public class ContentChannelService extends BaseChannelAdaptService implements Cloneable  {

    @Autowired
    ContentService contentService;

    @Autowired
    MileluConfig thinkItProperties;

    Map<String,Object> params = new HashMap<>();

    @Override
    public void notifyIt(Map<String,Object> params){
        System.out.println("========================通知内容创建============================");
        params.put(Channel.SITE_ID, ChannelThreadLocal.get(Channel.SITE_ID));
        this.params = params;
        execuate();
    }

    @Override
    public void notifyIt(Map<String,Object> params, Boolean notify){
        params.put(Channel.SITE_ID, ChannelThreadLocal.get(Channel.SITE_ID));
        this.params = params;
        if(notify){
            execuate(true,true);
        }else {
            execuate();
        }
    }

    @Override
    protected String loadTempPath() {
        return this.tempPath;
    }

    @Override
    protected String loadDestPath() {
        return this.destPath;
    }

    @Override
    protected Map<String, Object> loadTempParams(Map<String,Object> variable) {
        Map<String,Object> params = contentService.loadTempParams(this.params, Arrays.asList("0","1"));
        setPath(params);
        return params;
    }

    @Override
    public void setPath(Map<String,Object> params){
        if(CommonUtils.BeNotNull(params) && !params.isEmpty()){
            boolean hasTemp=params.containsKey(Channel.TEMP_PATH) && CommonUtils.BeNotNull(params.get(Channel.TEMP_PATH));
            boolean hasDest=params.containsKey(Channel.DEST_PATH) && CommonUtils.BeNotNull(params.get(Channel.DEST_PATH));
            boolean hasPathRule=params.containsKey(Channel.PATH_RULE) && CommonUtils.BeNotNull(params.get(Channel.PATH_RULE));
            if(hasTemp){
                this.tempPath =params.get(Channel.TEMP_PATH).toString();
            }
            if(hasDest){
                String templatePath =thinkItProperties.getSitePath();
                String destPath = params.get(Channel.DEST_PATH).toString();
                String url = params.get(Channel.URL).toString();
                if(hasPathRule){
                    this.destPath = templatePath+destPath+url;
                    params.put(Channel.DEST_PATH,this.destPath);
                }
            }
        }
    }


    @Override
    public ChannelEnum getName() {
        return ChannelEnum.CONTENT;
    }
}
