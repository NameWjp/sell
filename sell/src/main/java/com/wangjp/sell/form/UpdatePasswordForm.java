package com.wangjp.sell.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/10/9 17:02
 * @detail
 */
@Data
public class UpdatePasswordForm {

    @NotBlank(message = "旧密码不能为空")
    @ApiModelProperty(value = "旧密码")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码")
    private String newPassword;
}
