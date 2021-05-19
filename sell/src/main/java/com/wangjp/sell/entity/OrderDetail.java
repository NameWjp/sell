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
 * @date 2021/3/3 9:57 下午
 * @detail 订单详情实体类
 */
@Entity
@Data
// 用自己的属性和从父类继承的属性 来生成 hashcode
@EqualsAndHashCode(callSuper = true)
// 用自己的属性和从父类继承的属性 来生成 string
@ToString(callSuper = true)
public class OrderDetail extends AbstractAuditModel {

    // 订单详情id
    @Id
    private String id;

    // 订单id
    private String orderId;

    // 商品id
    private String productId;

    // 商品名称
    private String productName;

    // 商品价格
    private BigDecimal productPrice;

    // 商品数量
    private Integer productQuantity;

    // 商品小图
    private String productIcon;
}
