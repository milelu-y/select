package com.milelu.freemark.processor.message;

import lombok.Data;

/**
 * @author MILELU
 * @date 2021/1/15 15:12
 */
@Data
public class MessageModel {
    public MessageModel(Object message, String channel, String siteId, String domain) {
        this.message = message;
        this.channel = channel;
        this.siteId = siteId;
        this.domain = domain;
    }

    public MessageModel(Object message, String channel) {
        this.message = message;
        this.channel = channel;
    }

    public MessageModel(Object message, String channel, String domain) {
        this.message = message;
        this.channel = channel;
        this.domain = domain;
    }


    private Object message;

    private String channel;

    private String method;

    private String siteId;

    private String domain;
}
