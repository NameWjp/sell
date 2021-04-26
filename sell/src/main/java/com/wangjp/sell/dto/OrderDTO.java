package com.wangjp.sell.dto;

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
public class OrderDTO<T> {

    // 订单id
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
    private Integer orderStatus;

    // 支付状态
    private Integer payStatus;

    // 创建时间
    @CreatedDate
    private Date createTime;

    // 修改时间
    @LastModifiedDate
    private Date updateTime;

    // 详情列表
    List<T> detailList;
}
