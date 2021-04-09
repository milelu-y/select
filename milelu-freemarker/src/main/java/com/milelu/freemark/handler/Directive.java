package com.milelu.freemark.handler;

import com.milelu.freemark.emums.DirectiveEnum;

import java.io.IOException;

/**
 *
 * BaseDirective 指令接口
 *
 */
public interface Directive {

    /**
     * @param handler
     * @throws IOException
     * @throws Exception
     */
     void execute(RenderHandler handler) throws IOException, Exception;

    /**
     * 指定指令名称
     * @return
     */
     DirectiveEnum getName();
}
