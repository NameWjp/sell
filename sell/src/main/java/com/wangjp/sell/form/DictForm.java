package com.wangjp.sell.form;

import com.wangjp.sell.groups.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/5/23 12:31 下午
 * @detail
 */
@Data
public class DictForm {

    @NotBlank(message = "字典编号不能为空")
    @ApiModelProperty(value = "字典编号")
    private String dictCode;

    @NotNull(message = "字典名称不能为空")
    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @NotNull(message = "字典值不能为空")
    @ApiModelProperty(value = "字典值")
    private String dictValue;

    @NotNull(message = "父节点id不能为空", groups = Update.class)
    @ApiModelProperty(value = "父节点id")
    private Integer parentId;
}
