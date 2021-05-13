package com.wangjp.sell.form;

import com.wangjp.sell.annotation.EnumValueValidator;
import com.wangjp.sell.enums.IsEnableEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

    @NotBlank(message = "用户名称不能为空")
    @ApiModelProperty(value = "用户名称")
    private String username;

    @NotNull(message = "是否启用不能为空")
    @ApiModelProperty(value = "是否启用 1启用2停用")
    @EnumValueValidator(enumClass = IsEnableEnum.class, message = "是否启用不在枚举范围内")
    private Integer isEnable;
}
