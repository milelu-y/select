package com.milelu.freemark.processor.resove;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import com.milelu.common.constant.Channel;
import com.milelu.common.utils.CommonUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author Milelu
 */
public abstract class ResolveHandlerAdapt {

    private Object message;

    @Getter @Setter
    private String siteId;

    @Getter @Setter
    private String userId;

    public ResolveHandlerAdapt(Object message){
        if(CommonUtils.BeNotNull(message)){
            this.message = message;
        }
        this.siteId = getString(Channel.SITE_ID);
        this.userId = getString(Channel.USER_ID);
    }

    protected String getString(String key){
        if(message instanceof Map){
            Object data = geMap().get(key);
            if(CommonUtils.BeNotNull(data)){
                return data.toString();
            }
        }
        return null;
    }

    protected String getMethod(){
       if(message instanceof Map){
           Object method = geMap().get(Channel.METHOD);
           if(CommonUtils.BeNotNull(method)){
               return method.toString();
           }
       }
       return null;
    }

    protected Object[] getArgs(){
        if(message instanceof Map){
            Object args = geMap().get(Channel.ARGS);
            if(CommonUtils.BeNotNull(args)){
                if(args instanceof Object[]){
                    return (Object[]) args;
                }
            }
        }
        return null;
    }

    protected Map geMap(){
        return result();
    }

    private <T> T result(){
        if(CommonUtils.BeNotNull(message)){
            return (T) message;
        }
        return null;
    }
}
