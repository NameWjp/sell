package com.wangjp.sell.repository;

import com.wangjp.sell.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/3/3 10:15 下午
 * @detail 订单详情映射
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrderId(String OrderId);
}
