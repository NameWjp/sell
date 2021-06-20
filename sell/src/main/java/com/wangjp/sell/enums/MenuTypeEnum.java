package com.wangjp.sell.enums;

import lombok.Getter;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/6 5:13 下午
 * @detail
 */
@Getter
public enum  MenuTypeEnum {

    PAGE(1, "页面"),
    BUTTON(2, "按钮");

    private Integer code;

    private String message;

    MenuTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
