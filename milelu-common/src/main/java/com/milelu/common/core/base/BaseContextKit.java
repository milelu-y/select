package com.milelu.common.core.base;

import com.milelu.common.constant.Constants;
import sun.security.util.SecurityConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author MILELU
 * @date 2021/1/15 14:59
 */
public class BaseContextKit {

    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        return map.get(key) == null ? "" : map.get(key);
    }

    public static String getUserId() {
        Object value = get("userId");
        String res = returnObjectValue(value);
        return res;
    }

    public static String getOrgId() {
        Object value = get("orgId");
        String res = returnObjectValue(value);
        return res;
    }


    public static String getUserClient() {
        Object value = get("userClient");
        String res = returnObjectValue(value);
        return res;
    }

    public static String getAccount() {
        Object value = get("userAccount");
        String res = returnObjectValue(value);
        return res;
    }

    public static String getSiteId() {
        Object siteId = get(Constants.DEFAULT_SITE);
        return returnObjectValue(siteId);
    }


    public static void setUserId(String userID) {
        set("userId", userID);
    }

    public static void setOrgId(String orgId) {
        set("orgId", orgId);
    }

    public static void setAccount(String userName) {
        set("userClient", userName);
    }

    public static void setRoleSign(Set<String> roleSign) {
        set("userAccount", roleSign);
    }

    public static void setSiteId(String siteId) {
        set(Constants.DEFAULT_SITE, siteId);
    }

    public static Set<String> getRoleSign() {
        Object object= get("userRoleSign");
        return (Set<String>)object;
    }


    private static String returnObjectValue(Object value) {
        return value == null ? null : value.toString();
    }

    public static void remove() {
        threadLocal.remove();
    }

    public static void setUserClient(String clientId) {
        set("userClient", clientId);
    }

}
