package com.wangjp.sell.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/29 9:47 下午
 * @detail
 */
@Data
public class OnlyIntIdForm {

    @NotNull(message = "id不能为空")
    private Integer id;
}
