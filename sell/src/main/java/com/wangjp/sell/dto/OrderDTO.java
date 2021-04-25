package com.wangjp.sell.dto;

import com.wangjp.sell.entity.OrderDetail;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/3/4 8:47 下午
 * @detail 数据传输对象 dto，用于 service 传输数据
 */
@Data
public class OrderDTO {

    // 订单id
    private String orderId;

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

    // 订单状态: 0新下单 1完结 2已取消
    private Integer orderStatus;

    // 支付状态: 0待支付 1支付成功
    private Integer payStatus;

    // 创建时间
    @CreatedDate
    private Date createTime;

    // 修改时间
    @LastModifiedDate
    private Date updateTime;

    // 订单详情
    List<OrderDetail> orderDetailList;
}
