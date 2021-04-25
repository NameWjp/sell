package com.wangjp.sell.dto;

import lombok.Data;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/3/7 9:18 下午
 * @detail 购物车
 */
@Data
public class CartDTO {

    // 商品 id
    private String productId;

    // 商品数量
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
