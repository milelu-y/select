package com.milelu.freemark.processor.channel;

import cn.hutool.core.io.FileUtil;
import com.milelu.common.config.MileluConfig;
import com.milelu.common.constant.Channel;
import com.milelu.common.core.base.BaseContextKit;
import com.milelu.common.core.domain.AjaxResult;
import com.milelu.common.utils.CommonUtils;
import com.milelu.freemark.component.AbstractNotify;
import com.milelu.freemark.component.NotifyComponent;
import com.milelu.freemark.component.TemplateComponent;
import com.milelu.freemark.emums.ChannelEnum;
import com.milelu.freemark.handler.DirectiveInterceptor;
import com.milelu.freemark.handler.RenderHandler;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.milelu.common.core.base.BaseContextKit.getSiteId;
import static com.milelu.common.core.base.BaseContextKit.getUserId;

/**
 * @author MILELU
 * @date 2021/1/15 15:14
 */
@Slf4j
public abstract class BaseChannelAdaptService implements BaseChannelService {

    protected String tempPath;

    protected String destPath;

    @Autowired
    protected NotifyComponent notifyComponent;

    @Autowired
    protected DirectiveInterceptor interceptor;

    @Autowired
    protected MileluConfig mileluConfig;

    @Autowired
    protected TemplateComponent templateComponent;

    @Override
    public ChannelEnum getName() {
        return null;
    }

    @Override
    public void notifyIt(String id) {

    }

    @Override
    public void notifyIt(String id, Boolean notify) {

    }

    @Override
    public void notifyIt(List<Map<String,Object >> params,Long total,Integer pageNo,Integer pageSize){};

    @Override
    public void notifyIt() {

    }

    @Override
    public void notifyIt(Boolean notify) {
        System.out.println("1321");
    }

    @Override
    public void notifyIt(Map<String, Object> params) {

    }

    @Override
    public void notifyIt(List params) {

    }

    @Override
    public void notifyIt(Map<String, Object> params, Boolean notify) {

    }

    @Override
    public void notifyIt(RenderHandler renderHandler) {

    }

    @Override
    public AjaxResult execuate() {
        return doIt(null);
    }

    private AjaxResult doIt(Map<String, Object> variable) {
        BaseContextKit.setSiteId(getSiteId());
        BaseContextKit.setUserId(getUserId());
        Map<String, Object> params = loadTempParams(variable);
        if (params != null) {
            params.put(Channel.CHANNER_NAME, getName().getName());
            Configuration configuration = interceptor.injectionShareVariable(variable);
            String destPath = loadDestPath();
            if (continueNotify(tempPath, destPath)) {
                return templateComponent.createStaticFile(format(tempPath), format(destPath), params, configuration);
            } else {
                log.error("抱歉,系统检测您的模板或者目标目录不存在，请核对后再次生成");
            }
        }
        return AjaxResult.error("模板不存在！");
    }

    protected abstract String loadTempPath();

    protected abstract String loadDestPath();

    private boolean continueNotify(String tempPath, String destPath) {
        String finalTempPath = mileluConfig.getTemplate() + tempPath;
        return CommonUtils.BeNotBlank(tempPath) && CommonUtils.BeNotBlank(destPath) && FileUtil.exist(finalTempPath);
    }

    private String format(String path) {
        if (CommonUtils.BeNotBlank(path)) {
            return path.replace("\\", "/");
        }
        return null;
    }

    protected abstract Map<String, Object> loadTempParams(Map<String, Object> variable);

    @Override
    public AjaxResult execuate(Map<String, Object> variable) {
        return doIt(variable);
    }

    @Override
    public AjaxResult execuate(Map<String, Object> variable, Boolean notify) {
        AjaxResult apiResult = execuate(variable);
        if(notify){
            apiResult.putAll(variable);
            notifyComponent.notifyMsg(getUserId(),apiResult);
        }
        return apiResult;
    }

    @Override
    public AjaxResult execuate(AbstractNotify notify) {
        AjaxResult apiResult = execuate();
        notify.notifyMsg(getUserId(), apiResult);
        return apiResult;
    }
    @Override
    public void execuate(boolean afterCall) {
        AjaxResult ajaxResult = null;
        try {
            ajaxResult = execuate();
        } finally {
            if (afterCall) {
                callBack(ajaxResult);
            }
        }
    }

    @Override
    public void execuate(boolean afterCall, boolean notify) {
        AjaxResult apiResult = null;
        try {
            apiResult = execuate();
        } finally {
            if (notify) {
                notifyComponent.notifyMsg(getUserId(), apiResult);
            }
            if (afterCall) {
                callBack(apiResult);
            }
        }
    }

    @Override
    public void execuate(boolean afterCall, String... variableName) {
        AjaxResult ajaxResult = null;
        try {
            ajaxResult = execuate();
        } finally {
            if (afterCall) {
                callBack(ajaxResult);
            }
        }
    }

    @Override
    public void execuate(AbstractNotify notify, boolean callBack) {
        AjaxResult ajaxResult = execuate();
//        notify.notifyMsg(getUserId(), ajaxResult);
        if (callBack) {
            callBack(ajaxResult);
        }
    }

    @Override
    public void callBack(Object param) {

    }

    @Override
    public BaseChannelService clone(boolean singleton) throws CloneNotSupportedException {
        if (singleton) {
            return this;
        }
        return (BaseChannelService) super.clone();
    }

    protected void setPath(Map<String, Object> params) {
        if (CommonUtils.BeNotNull(params) && !params.isEmpty()) {
            boolean hasTemp = params.containsKey(Channel.TEMP_PATH) && CommonUtils.BeNotNull(params.get(Channel.TEMP_PATH));
            boolean hasDest = params.containsKey(Channel.DEST_PATH) && CommonUtils.BeNotNull(params.get(Channel.DEST_PATH));
            if (hasTemp) {
                this.tempPath = params.get(Channel.TEMP_PATH).toString();
            }
            if (hasDest) {
                this.destPath = params.get(Channel.DEST_PATH).toString();
            }
        }
    }

    protected void setPath(Map<String, Object> params, Boolean absoutePath) {
        if (!absoutePath) {
            if (CommonUtils.BeNotNull(params) && !params.isEmpty()) {
                boolean hasDest = params.containsKey(Channel.DEST_PATH) && CommonUtils.BeNotNull(params.get(Channel.DEST_PATH));
                if (hasDest) {
                    String destPath = params.get(Channel.DEST_PATH).toString();
                    if (!destPath.contains(mileluConfig.getSitePath())) {
                        String finalDestPath = mileluConfig.getSitePath() + params.get(Channel.DEST_PATH).toString();
                        params.put(Channel.DEST_PATH, finalDestPath);
                    }
                }
            }
        }
        setPath(params);
    }
    protected String getUserId(){
        Object userId = ChannelThreadLocal.get(Channel.USER_ID);
        if(CommonUtils.BeNotNull(userId)){
            return userId.toString();
        }
        return null;
    }

}
