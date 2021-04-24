package com.wangjp.sell.service.impl;

import com.wangjp.sell.entity.ProductInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    void findOne() {
        ProductInfo productInfo = productService.findOne("1");
        Assertions.assertEquals("1", productInfo.getProductId());
    }

    @Test
    void findUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assertions.assertNotEquals(0, productInfoList.size());
    }

    @Test
    void findAll() {
        PageRequest request = PageRequest.of(0, 10);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
        Assertions.assertNotEquals(0, productInfoPage.getTotalElements());
    }

    @Test
    void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("3");
        productInfo.setProductName("南瓜粥");
        productInfo.setProductPrice(new BigDecimal("3.2"));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("南瓜粥超级好喝");
        productInfo.setProductIcon("http://test.png");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(4);
        ProductInfo result = productService.save(productInfo);
        Assertions.assertNotNull(result);
    }
}