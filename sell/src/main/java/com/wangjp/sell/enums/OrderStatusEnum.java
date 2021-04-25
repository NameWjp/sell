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

    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "取消");

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
