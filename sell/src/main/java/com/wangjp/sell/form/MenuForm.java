package com.wangjp.sell.form;

import com.wangjp.sell.annotation.EnumValueValidator;
import com.wangjp.sell.enums.MenuTypeEnum;
import com.wangjp.sell.groups.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/6 5:40 下午
 * @detail
 */
@Data
public class MenuForm {

    @ApiModelProperty(value = "菜单url")
    private String url;

    @NotNull(message = "菜单类型不能为空")
    @ApiModelProperty(value = "菜单类型")
    @EnumValueValidator(enumClass = MenuTypeEnum.class, message = "菜单类型不在枚举范围内")
    private Integer type;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "父节点id")
    private Integer parentId;

    @NotNull(message = "排序不能为空")
    @ApiModelProperty(value = "排序")
    private Integer sort;

    @NotNull(message = "菜单编码不能为空")
    @ApiModelProperty(value = "菜单编码")
    private String code;
}
