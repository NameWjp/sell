package com.wangjp.sell.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/5 5:07 下午
 * @detail
 */
@Data
public class RoleForm {

    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称")
    private String name;

    @NotNull(message = "权限配置不能为空")
    @ApiModelProperty(value = "权限配置")
    private List<Integer> privilegeIds;

    @ApiModelProperty(value = "角色描述")
    private String description;
}
