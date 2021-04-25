package com.wangjp.sell.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/3/3 9:57 下午
 * @detail 订单详情实体类
 */
@Entity
@Data
// 开启 jpa 审计功能（用于自动添加时间等）
@EntityListeners(AuditingEntityListener.class)
public class OrderDetail {

    // 订单详情id
    @Id
    private String detailId;

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

    // 创建时间
    @CreatedDate
    private Date createTime;

    // 修改时间
    @LastModifiedDate
    private Date updateTime;
}
