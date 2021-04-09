package com.milelu.service.service.channel;

import com.milelu.common.config.MileluConfig;
import com.milelu.common.constant.Channel;
import com.milelu.freemark.emums.ChannelEnum;
import com.milelu.freemark.processor.channel.BaseChannelAdaptService;
import com.milelu.freemark.processor.message.MessageSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MILELU
 * @date 2021/3/16 17:16
 */
@Service
public class TemplateChannelService extends BaseChannelAdaptService implements Cloneable{
    @Autowired
    MessageSend messageSend;

    @Autowired
    MileluConfig mileluConfig;

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
        Map<String,Object> map = new HashMap<>();
        String sitePath = mileluConfig.getSitePath() + mileluConfig.getDomain();
        map.put(Channel.TEMP_PATH,variable.get("relativePath"));
        String path= (String) variable.get("relativePath");
        map.put(Channel.DEST_PATH, sitePath+path.replace("\\","/"));
        setPath(map);
        return map;
    }


    @Override
    public ChannelEnum getName() {
        return ChannelEnum.TEMPLATE;
    }

    @Override
    public void notifyIt(Map<String, Object> params, Boolean notify) {
        execuate(params,notify);
    }

}
