package com.wangjp.sell.enums;

import lombok.Getter;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/29 12:13 上午
 * @detail
 */
@Getter
public enum IsEnableEnum {
    ENABLED(1, "启用"),
    DISABLED(2, "停用");

    private Integer code;

    private String message;

    IsEnableEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
