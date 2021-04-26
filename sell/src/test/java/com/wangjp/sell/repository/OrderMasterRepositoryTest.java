package com.wangjp.sell.repository;

import com.wangjp.sell.entity.OrderMaster;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

@SpringBootTest
class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setId("456");
        orderMaster.setBuyerName("小明");
        orderMaster.setBuyerPhone("123321");
        orderMaster.setBuyerAddress("杭州市西湖区");
        orderMaster.setBuyerOpenid("1231232");
        orderMaster.setOrderAmount(new BigDecimal("2.34"));

        OrderMaster result = repository.save(orderMaster);
        Assertions.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() {
        PageRequest request = PageRequest.of(0, 10);
        Page<OrderMaster> result = repository.findByBuyerOpenid("1231232", request);
        Assertions.assertNotEquals(0, result.getTotalElements());
    }
}