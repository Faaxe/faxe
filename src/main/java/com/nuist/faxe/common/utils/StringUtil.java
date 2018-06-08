package com.nuist.faxe.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * 字符串工具类
 *
 * @author ZhouXiang
 **/
public class StringUtil {
    /**
     * 返回 6 位随机字符串
     * @return
     */
    public static String randomUUID() {
        return StringUtils.replace(UUID.randomUUID().toString().substring(0, 6), "-", "");
    }

}
