package com.wangjp.sell.service.impl;

import com.wangjp.sell.dto.OrderDTO;
import com.wangjp.sell.entity.OrderDetail;
import com.wangjp.sell.entity.OrderMaster;
import com.wangjp.sell.enums.OrderStatusEnum;
import com.wangjp.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "110110";

    private final String ORDER_ID = "1618929242555931603";

    @Test
    void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("林俊杰");
        orderDTO.setBuyerAddress("杭州市西湖区三墩镇");
        orderDTO.setBuyerPhone("15555555555");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        // 购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail o1 = new OrderDetail();
        o1.setProductId("1");
        o1.setProductQuantity(3);

        orderDetailList.add(o1);

        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单成功】 result={}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    void findOne() {
        OrderDTO result = orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】result={}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    void findList() {
        PageRequest request = PageRequest.of(0, 10);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID, request);
        Assertions.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

    @Test
    void cancel() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(ORDER_ID);
        OrderMaster result = orderService.cancel(orderDTO);
        Assertions.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    void finish() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(ORDER_ID);
        OrderMaster result = orderService.finish(orderDTO);
        Assertions.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    void paid() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(ORDER_ID);
        OrderMaster result = orderService.paid(orderDTO);
        Assertions.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
    }
}