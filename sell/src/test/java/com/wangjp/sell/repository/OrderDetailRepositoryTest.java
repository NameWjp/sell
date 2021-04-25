package com.wangjp.sell.repository;

import com.wangjp.sell.entity.OrderDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest() {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("456");
        orderDetail.setOrderId("123");
        orderDetail.setProductIcon("http://xxx.jpg");
        orderDetail.setProductId("123");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(new BigDecimal("1.2"));
        orderDetail.setProductQuantity(2);

        OrderDetail result = repository.save(orderDetail);
        Assertions.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList = repository.findByOrderId("123");
        System.out.println(orderDetailList);
    }
}