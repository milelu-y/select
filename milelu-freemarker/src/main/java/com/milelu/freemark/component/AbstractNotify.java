package com.milelu.freemark.component;

import lombok.extern.slf4j.Slf4j;

/**
 * 通知组件
 */
@Slf4j
public abstract class AbstractNotify {
    /**
     * 所有通知全部成功
     * @param principal
     * @param data
     */
    public abstract void notifyMsg(String principal, Object data);

}
