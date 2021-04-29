package com.wangjp.sell.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/29 9:49 下午
 * @detail
 */
@Data
public class OnlyStringIdForm {

    @NotBlank(message = "id不能为空")
    private String id;
}
