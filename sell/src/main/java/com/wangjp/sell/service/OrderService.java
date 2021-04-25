package com.wangjp.sell.service;

import com.wangjp.sell.dto.OrderDTO;
import com.wangjp.sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/3/4 8:35 下午
 * @detail
 */
public interface OrderService {

    // 创建订单
    OrderDTO create(OrderDTO orderDTO);

    // 查询单个订单
    OrderDTO findOne(String orderId);

    // 查询订单列表
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    // 取消订单
    OrderMaster cancel(OrderDTO orderDTO);

    // 完结订单
    OrderMaster finish(OrderDTO orderDTO);

    // 支付订单
    OrderMaster paid(OrderDTO orderDTO);
}
