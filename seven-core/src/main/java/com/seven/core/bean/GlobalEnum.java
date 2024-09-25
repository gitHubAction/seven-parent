package com.seven.core.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.seven.core.Constants;

import java.util.Map;

/**
 * @author zhangshihao01
 * @version 1.0
 * @description
 * @date 2024/9/25 14:14
 */
public enum GlobalEnum {

    E9009("E9009", "[E9009]未找到对应方法");
    /**
     * 枚举码
     */
    public final String code;
    /**
     * 枚举描述
     */
    public final String text;

    GlobalEnum(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getText(String... values) {
        String msg = text;
        for (String val : values) {
            msg = msg.replaceFirst(Constants.PLACE_HOLDER, val);
        }
        return msg;
    }

    /**
     * 根据枚举码获取枚举
     *
     * @param code 枚举码
     * @return 枚举
     */
    @JsonCreator
    public static GlobalEnum of(String code) {
        for (GlobalEnum commonEnum : values()) {
            if (commonEnum.code.equals(code)) {
                return commonEnum;
            }
        }
        return null;
    }

    @JsonValue
    public Map<String, String> value() {
        return ImmutableMap.<String, String>builder()
                .put("code", code)
                .put("text", text)
                .build();
    }
}
