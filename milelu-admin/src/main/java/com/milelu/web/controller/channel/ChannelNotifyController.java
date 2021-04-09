package com.milelu.web.controller.channel;

import com.milelu.service.service.category.ChannelNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MILELU
 * @date 2021/1/15 15:28
 */
@Validated
@RestController
@RequestMapping("/channelNotify")
public class ChannelNotifyController {
    @Autowired
    ChannelNotifyService channelNotifyService;

    /**
     * 手动生成首页
     */
    @PutMapping("/notifyHomePage")
    public void notifyHomePage() {
        channelNotifyService.notifyHomePage();
    }


    /**
     * 手动生成全站
     */
    @PutMapping("/notifyWholeSite")
    public void notifyWholeSite(){
        channelNotifyService.notifyWholeSite();
    }

    /**
     * 手动生成全站
     */
    @PutMapping("/notifyCategory")
    public void notifyCategory(){
        channelNotifyService.notifyCategory();
    }

}
