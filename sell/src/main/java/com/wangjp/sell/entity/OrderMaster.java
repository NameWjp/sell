package com.wangjp.sell.entity;

import com.wangjp.sell.entity.base.AbstractAuditModel;
import com.wangjp.sell.enums.OrderStatusEnum;
import com.wangjp.sell.enums.PayStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/3/3 8:45 下午
 * @detail 订单表实体类
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderMaster extends AbstractAuditModel {

    // 订单id
    @Id
    private String id;

    // 买家名称
    private String buyerName;

    // 买家电话
    private String buyerPhone;

    // 买家地址
    private String buyerAddress;

    // 买家微信 openid
    private String buyerOpenid;

    // 订单总金额
    private BigDecimal orderAmount;

    // 订单状态
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    // 支付状态
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
}
