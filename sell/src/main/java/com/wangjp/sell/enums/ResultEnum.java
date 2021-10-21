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

    USER_OLD_PASSWORD_ERROR(45, "旧密码错误"),

    USER_ADMIN_UNABLE_DELETE(46, "管理员不能删除"),

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

    CATEGORY_NOT_EXIST(101, "商品类型不存在"),

    CATEGORY_ID_NOT_EMPTY(102, "商品类型id不能为空"),

    CATEGORY_DELETE_EXIST_PRODUCT_INFO(103, "删除的商品类型下存在商品，请先删除商品"),

    CATEGORY_CODE_REPEAT(104, "商品类型编号重复"),

    PRODUCT_INFO_NOT_EXIST(111, "商品不存在"),

    PRODUCT_INFO_ID_NOT_EMPTY(112, "商品id不能为空"),

    COMMON_IMAGE_FORMAT_ERROR(121, "图片格式错误"),

    COMMON_UPLOAD_ROOT_PATH_NOT_FIND(122, "上传根目录未找到"),

    COMMON_IMAGE_SAVE_ERROR(123, "图片保存出错"),

    COMMON_IMAGE_NOT_FIND(124, "未上传任何图片"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
