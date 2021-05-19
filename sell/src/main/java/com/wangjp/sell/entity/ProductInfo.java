package com.wangjp.sell.entity;

import com.wangjp.sell.entity.base.AbstractAuditModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/2/24 8:51 下午
 * @detail 商品详情实体类
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductInfo extends AbstractAuditModel {

    // 商品id
    @Id
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
}
