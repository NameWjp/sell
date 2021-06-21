package com.wangjp.sell.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/21 9:34 下午
 * @detail
 */
@Data
public class OrganForm {

    @NotBlank(message = "组织机构名称不能为空")
    @ApiModelProperty(value = "组织机构名称")
    private String name;

    @ApiModelProperty(value = "父节点id")
    private Integer parentId;
}
