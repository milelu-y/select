package com.milelu.service.service.channel;

import com.milelu.common.config.MileluConfig;

import com.milelu.common.constant.Channel;
import com.milelu.common.utils.CommonUtils;
import com.milelu.freemark.emums.ChannelEnum;
import com.milelu.freemark.processor.channel.BaseChannelAdaptService;
import com.milelu.service.service.content.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Milelu
 */
@Service
public class ContentAllChannelService extends BaseChannelAdaptService implements Cloneable {

    @Autowired
    ContentService contentService;

    @Autowired
    MileluConfig mileluConfig;

    List<Map<String, Object>> params = new ArrayList<>();

    @Override
    public void notifyIt(List<Map<String, Object>> params, Long total, Integer pageNo, Integer pageSize) {
        this.params = params;
        if (!params.isEmpty()) {
            Map<String, Object> map = new HashMap(8);
            for (int i = 0; i < params.size(); i++) {
                map.put("index_num", i);
                long rowNo = ((pageNo - 1) * pageSize) + i;
                BigDecimal rate = new BigDecimal((rowNo + 1)).
                        divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_UP).
                        multiply(new BigDecimal(100));
                map.put(Channel.PERCENT, rate.doubleValue());
                execuate(map, true);
            }
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
    protected Map<String, Object> loadTempParams(Map<String, Object> variable) {
        if (!params.isEmpty()) {
//            contentService.loadTempParams(this.params, Arrays.asList("0", "1"));
            Integer index = (Integer) variable.get("index_num");
            Map<String, Object> ret = params.get(index);
            setPath(ret);
            return ret;
        }
        return null;
    }

    @Override
    public void setPath(Map<String, Object> params) {
        if (CommonUtils.BeNotNull(params) && !params.isEmpty()) {
            boolean hasTemp = params.containsKey(Channel.TEMP_PATH) && CommonUtils.BeNotNull(params.get(Channel.TEMP_PATH));
            boolean hasDest = params.containsKey(Channel.DEST_PATH) && CommonUtils.BeNotNull(params.get(Channel.DEST_PATH));
            boolean hasPathRule = params.containsKey(Channel.PATH_RULE) && CommonUtils.BeNotNull(params.get(Channel.PATH_RULE));
            if (hasTemp) {
                this.tempPath = params.get(Channel.TEMP_PATH).toString();
            }
            if (hasDest) {
                String templatePath = mileluConfig.getSitePath();
                String destPath = params.get(Channel.DEST_PATH).toString();
                String url = params.get(Channel.URL).toString();
                if (hasPathRule) {
                    this.destPath = templatePath + destPath + url;
                    params.put(Channel.DEST_PATH, this.destPath);
                }
            }
        }
    }


    @Override
    public ChannelEnum getName() {
        return ChannelEnum.CONTENTS;
    }
}
