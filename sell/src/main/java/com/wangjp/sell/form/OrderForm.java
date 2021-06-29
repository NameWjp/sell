package com.wangjp.sell.form;

import com.wangjp.sell.dto.CartDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/24 3:28 下午
 * @detail
 */
@Data
public class OrderForm {

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名")
    private String name;

    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号")
    @Pattern(regexp = "^[1][3,4,5,7,8,9][0-9]{9}$", message = "手机号格式不正确")
    private String phone;

    @NotBlank(message = "地址不能为空")
    @ApiModelProperty(value = "地址")
    private String address;

    @NotBlank(message = "openid不能为空")
    @ApiModelProperty(value = "openid")
    private String openid;

    @NotNull(message = "购物车不能为空")
    @ApiModelProperty(value = "购物车")
    private List<CartDTO> cartList;
}
