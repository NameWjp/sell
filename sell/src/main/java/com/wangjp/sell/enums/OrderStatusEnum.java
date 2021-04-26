package com.wangjp.sell.enums;

import lombok.Getter;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/3/3 9:45 下午
 * @detail 订单状态
 */
@Getter
public enum OrderStatusEnum {

    NEW(1, "新订单"),
    FINISHED(2, "完结"),
    CANCEL(3, "取消");

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
