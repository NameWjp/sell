package com.wangjp.sell.vo;

import lombok.Data;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/2/27 3:59 下午
 * @detail 显示层对象，用于视图显示。
 * http请求返回的最外层对象，例如:
 * {
 *     "status": 200,
 *     "message": "成功",
 *     "data": {}
 * }
 */
@Data
public class ResultVO<T> {

    // 状态码
    private Integer status;

    // 提示信息
    private String message;

    // 具体内容
    private T data;
}
