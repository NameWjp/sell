package com.wangjp.sell.form;

import com.wangjp.sell.annotation.EnumValueValidator;
import com.wangjp.sell.enums.IsEnableEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @NotNull(message = "角色不能为空")
    @ApiModelProperty(value = "角色")
    private List<Integer> roleIds;

    @NotNull(message = "组织机构不能为空")
    @ApiModelProperty(value = "组织机构")
    private Integer organId;
}
