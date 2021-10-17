package com.wangjp.sell.enums;

import lombok.Getter;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/10/17 7:10 下午
 * @detail
 */
@Getter
public enum  CategorySortEnum {
    ASC(1, "升序"),
    DESC(2, "降序");

    private Integer code;

    private String message;

    CategorySortEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
