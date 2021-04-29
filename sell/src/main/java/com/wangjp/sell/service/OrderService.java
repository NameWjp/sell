package com.wangjp.sell.service;

import com.wangjp.sell.dto.CartDTO;
import com.wangjp.sell.dto.OrderDTO;
import com.wangjp.sell.entity.OrderDetail;
import com.wangjp.sell.entity.OrderMaster;
import com.wangjp.sell.form.OnlyStringIdForm;
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
    OrderDTO<CartDTO> create(OrderDTO<CartDTO> orderDTO);

    // 查询单个订单
    OrderDTO<OrderDetail> findById(String orderId);

    // 查询订单列表
    Page<OrderMaster> findList(String buyerOpenid, Pageable pageable);

    // 取消订单
    OrderMaster cancel(OnlyStringIdForm form);

    // 完结订单
    OrderMaster finish(OnlyStringIdForm form);

    // 支付订单
    OrderMaster paid(OnlyStringIdForm form);
}
