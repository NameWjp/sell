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
 * @date 2021/2/24 8:51 下午
 * @detail 商品详情实体类
 */
@Entity
@Data
// 开启 jpa 审计功能（用于自动添加时间等）
@EntityListeners(AuditingEntityListener.class)
public class ProductInfo {

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

    // 创建时间
    @CreatedDate
    private Date createTime;

    // 修改时间
    @LastModifiedDate
    private Date updateTime;
}
