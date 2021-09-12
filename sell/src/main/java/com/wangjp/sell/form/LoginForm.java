package com.wangjp.sell.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/9/12 5:53 下午
 * @detail
 */
@Data
public class LoginForm {
    @NotBlank(message = "账户名称不能为空")
    @ApiModelProperty(value = "账户名称")
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;
}
