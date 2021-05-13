package com.wangjp.sell.enums;

import lombok.Getter;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/3/6 5:00 下午
 * @detail
 */
@Getter
public enum ResultEnum {

    PARAMS_ERROR(1, "参数不正确"),

    PRODUCT_NOT_EXIST(10, "商品不存在"),

    PRODUCT_STOCK_ERROR(11, "商品库存不足"),

    ORDER_NOT_EXIST(12, "订单不存在"),

    ORDER_DETAIL_NOT_EXIST(13, "订单详情不存在"),

    ORDER_STATUS_ERROR(14, "订单状态不正确"),

    ORDER_PAY_STATUS_ERROR(15, "订单支付状态不正确"),

    CART_EMPTY(16, "购物车不能为空"),

    USER_NOT_FIND(17, "未找到用户信息"),

    USER_ID_NOT_EMPTY(18, "用户id不能为空"),

    USER_ALREADY_EXIST(19, "用户已存在");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
