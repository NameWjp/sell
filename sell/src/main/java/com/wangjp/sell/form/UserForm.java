package com.wangjp.sell.form;

import com.wangjp.sell.annotation.EnumValueValidator;
import com.wangjp.sell.enums.IsEnableEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/27 9:58 下午
 * @detail
 */
@Data
public class UserForm {

    private Integer id;

    // 用户名称
    @NotBlank(message = "用户名称不能为空")
    private String username;

    // 用户密码
    @NotBlank(message = "用户密码不能为空")
    private String password;

    // 是否启用
    @NotNull(message = "是否启用不能为空")
    @EnumValueValidator(enumClass = IsEnableEnum.class, message = "是否启用不在枚举范围内")
    private Integer isEnable;
}
