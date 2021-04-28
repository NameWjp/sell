package com.wangjp.sell.service.impl;

import com.wangjp.sell.dto.CartDTO;
import com.wangjp.sell.dto.OrderDTO;
import com.wangjp.sell.entity.OrderDetail;
import com.wangjp.sell.entity.OrderMaster;
import com.wangjp.sell.enums.OrderStatusEnum;
import com.wangjp.sell.enums.PayStatusEnum;
import com.wangjp.sell.form.OrderForm;
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
        OrderDTO<CartDTO> orderDTO = new OrderDTO<>();
        orderDTO.setBuyerName("林俊杰");
        orderDTO.setBuyerAddress("杭州市西湖区三墩镇");
        orderDTO.setBuyerPhone("15555555555");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        // 购物车
        List<CartDTO> cartDTOList = new ArrayList<>();
        CartDTO o1 = new CartDTO("1", 3);
        cartDTOList.add(o1);

        orderDTO.setDetailList(cartDTOList);

        OrderDTO<CartDTO> result = orderService.create(orderDTO);
        log.info("【创建订单成功】 result={}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    void findById() {
        OrderDTO<OrderDetail> result = orderService.findById(ORDER_ID);
        log.info("【查询单个订单】result={}", result);
        Assertions.assertNotNull(result);
    }

    @Test
    void findList() {
        PageRequest request = PageRequest.of(0, 10);
        Page<OrderMaster> orderDTOPage = orderService.findList(BUYER_OPENID, request);
        Assertions.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

    @Test
    void cancel() {
        OrderForm orderForm = new OrderForm();
        orderForm.setId(ORDER_ID);
        OrderMaster result = orderService.cancel(orderForm);
        Assertions.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    void finish() {
        OrderForm orderForm = new OrderForm();
        orderForm.setId(ORDER_ID);
        OrderMaster result = orderService.finish(orderForm);
        Assertions.assertEquals(OrderStatusEnum.FINISHED.getCode(), result.getOrderStatus());
    }

    @Test
    void paid() {
        OrderForm orderForm = new OrderForm();
        orderForm.setId(ORDER_ID);
        OrderMaster result = orderService.paid(orderForm);
        Assertions.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
    }
}