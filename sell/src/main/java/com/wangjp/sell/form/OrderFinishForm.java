package com.wangjp.sell.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/26 9:54 下午
 * @detail
 */
@Data
public class OrderFinishForm {

    @NotBlank(message = "订单id不能为空")
    private String id;
}