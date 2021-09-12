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

    ORDER_NOT_EXIST(21, "订单不存在"),

    ORDER_DETAIL_NOT_EXIST(22, "订单详情不存在"),

    ORDER_STATUS_ERROR(23, "订单状态不正确"),

    ORDER_PAY_STATUS_ERROR(24, "订单支付状态不正确"),

    CART_EMPTY(31, "购物车不能为空"),

    USER_NOT_FIND(41, "未找到用户信息"),

    USER_ID_NOT_EMPTY(42, "用户id不能为空"),

    USER_ALREADY_EXIST(43, "用户已存在"),

    USER_USERNAME_ALREADY_EXIST(44, "用户名称已存在"),

    DICT_NOT_EXIST(51, "字典不存在"),

    DICT_PARENT_ID_NOT_EXIST(52, "字典父节点不存在"),

    DICT_ID_NOT_EMPTY(54, "字典id不能为空"),

    DICT_DELETE_EXIST_CHILDREN(55, "删除的字典存在子节点"),

    ROLE_ALREADY_EXIST(61, "角色已存在"),

    ROLE_NOT_FIND(62, "未找到角色信息"),

    ROLE_NAME_ALREADY_EXIST(63, "角色名称已存在"),

    ROLE_ID_NOT_EMPTY(64, "角色id不能为空"),

    MENU_CODE_ALREADY_EXIST(71, "字典值已存在"),

    MENU_PARENT_ID_NOT_EXIST(72, "菜单父节点不存在"),

    MENU_NOT_EXIST(73, "菜单不存在"),

    MENU_ID_NOT_EMPTY(74, "字典id不能为空"),

    MENU_DELETE_EXIST_CHILDREN(75, "删除的菜单存在子节点"),

    ORGAN_PARENT_ID_NOT_EXIST(81, "菜单父节点不存在"),

    ORGAN_NOT_EXIST(82, "组织机构不存在"),

    ORGAN_ID_NOT_EMPTY(83, "组织机构id不能为空"),

    ORGAN_DELETE_EXIST_CHILDREN(84, "删除的菜单存在子节点"),

    AUTH_USER_NOT_EXIST(91, "用户不存在"),

    AUTH_PASSWORD_ERROR(92, "密码错误"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
