package com.wangjp.sell.repository;

import com.wangjp.sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/3/3 10:05 下午
 * @detail 订单映射
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
