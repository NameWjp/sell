package com.wangjp.sell.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/10/17 11:03 上午
 * @detail
 */
@Data
public class CategoryForm {

    @NotBlank(message = "类目名称不能为空")
    @ApiModelProperty(value = "类目名称")
    private String name;

    @NotBlank(message = "类目编号不能为空")
    @ApiModelProperty(value = "类目编号")
    private String code;

    @NotNull(message = "排序不能为空")
    @ApiModelProperty(value = "排序")
    private Integer sort;
}
