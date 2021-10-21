package com.wangjp.sell.form;

import com.wangjp.sell.annotation.EnumValueValidator;
import com.wangjp.sell.enums.ProductStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/10/19 9:42
 * @detail
 */
@Data
public class ProductInfoForm {

    // 商品名称
    @NotBlank(message = "商品名称不能为空")
    @ApiModelProperty(value = "商品名称")
    private String productName;

    // 商品单价
    @NotNull(message = "商品单价不能为空")
    @ApiModelProperty(value = "商品单价")
    private BigDecimal productPrice;

    // 库存
    @NotNull(message = "商品库存不能为空")
    @ApiModelProperty(value = "商品库存")
    private Integer productStock;

    // 描述
    @ApiModelProperty(value = "商品描述")
    private String productDescription;

    // 小图
    @NotBlank(message = "商品图片不能为空")
    @ApiModelProperty(value = "商品图片")
    private String productIcon;

    // 状态
    @NotNull(message = "商品状态不能为空")
    @ApiModelProperty(value = "商品状态")
    @EnumValueValidator(enumClass = ProductStatusEnum.class, message = "商品状态不在枚举范围内")
    private Integer productStatus;

    // 类目编号
    @NotBlank(message = "商品类目编号不能为空")
    @ApiModelProperty(value = "商品类目编号")
    private String categoryCode;
}
