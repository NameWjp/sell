package com.wangjp.sell.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/10/21 17:05
 * @detail
 */
@Data
public class ProductInfoItemVO {

    // 商品id
    private String id;

    // 商品名称
    private String productName;

    // 商品单价
    private BigDecimal productPrice;

    // 库存
    private Integer productStock;

    // 描述
    private String productDescription;

    // 小图
    private String productIcon;

    // 状态
    private Integer productStatus;

    // 类目编号
    private String categoryCode;

    // 类目名称
    private String categoryName;

    // 创建时间
    private Date createTime;

    // 修改时间
    private Date updateTime;
}
