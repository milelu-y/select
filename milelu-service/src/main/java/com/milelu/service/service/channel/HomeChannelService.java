package com.milelu.service.service.channel;

import cn.hutool.core.io.FileUtil;
import com.milelu.common.constant.Channel;
import com.milelu.common.utils.CommonUtils;
import com.milelu.freemark.emums.ChannelEnum;
import com.milelu.freemark.processor.channel.BaseChannelAdaptService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MILELU
 * @date 2021/1/15 15:13
 */
@Service
public class HomeChannelService extends BaseChannelAdaptService implements Cloneable {


    @Override
    public void notifyIt(Boolean notify) {
        if (notify) {
            execuate(true, true);
        } else {
            execuate(true);
        }
    }
    @Override
    public void callBack(Object o){
        System.out.println(o);
    }

    @Override
    public void notifyIt() {
        execuate(true);
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
        Map<String, Object> params = new HashMap<>(8);
        //TODO:此处常量后期需要动态处理
        params.put("destPath", "/" + mileluConfig.getDomain() + "/index.html");
        params.put("indexFile", "/" + mileluConfig.getDomain() + "/index.html");
        params.put("href", "/index.html");
        params.put("templatePath", "/index.html");
//        createIndexFile(params);
        setPath(params, false);
        return params;
    }

    private void createIndexFile(Map<String, Object> map) {
        boolean hasKey = map.containsKey(Channel.INDEX_FILE) && map.containsKey(Channel.HREF);
        if (hasKey) {
            String indexFile = map.get(Channel.INDEX_FILE).toString(), href = map.get(Channel.HREF).toString();
            boolean create = CommonUtils.BeNotBlank(indexFile) && CommonUtils.BeNotBlank(href);
            if (create) {
                File file = FileUtil.newFile(mileluConfig.getSitePath() + indexFile);
                String content = "<script>window.location.href='" + href + "'</script>";
                FileUtil.writeString(content, file, Charset.forName("utf-8"));
            }
        }

    }

    @Override
    public ChannelEnum getName() {
        return ChannelEnum.HOME;
    }

}
