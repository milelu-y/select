package com.milelu.freemark.processor.channel;

import com.milelu.common.core.domain.AjaxResult;
import com.milelu.freemark.component.AbstractNotify;
import com.milelu.freemark.emums.ChannelEnum;
import com.milelu.freemark.handler.RenderHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface BaseChannelService {
    ChannelEnum getName();

    /**
     * 管道通知
     *
     * @param id : 可代表任意ID ru: content channel 就代表 content id 其他 代表各自的 id
     */
    void notifyIt(String id);

    void notifyIt(String id, Boolean notify);

    void notifyIt(List<Map<String, Object>> params, Long total, Integer pageNo, Integer pageSize);

    void notifyIt();

    void notifyIt(Boolean notify);

    void notifyIt(Map<String, Object> params);

    void notifyIt(List params);

    void notifyIt(Map<String, Object> params, Boolean notify);

    void notifyIt(RenderHandler renderHandler);

    AjaxResult execuate();

    AjaxResult execuate(Map<String, Object> variable);

    AjaxResult execuate(Map<String, Object> variable, Boolean notify);

    AjaxResult execuate(AbstractNotify notify);

    void execuate(boolean aftercall);

    void execuate(boolean aftercall, boolean notify);

    void execuate(boolean aftercall, String... variableName);

    void execuate(AbstractNotify notify, boolean callBack);

    void callBack(Object param);

    BaseChannelService clone(boolean singleton) throws CloneNotSupportedException;
}
