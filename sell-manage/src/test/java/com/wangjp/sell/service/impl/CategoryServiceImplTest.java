package com.wangjp.sell.service.impl;

import com.wangjp.sell.entity.ProductCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        Assertions.assertEquals(1, productCategory.getCategoryId());
    }

    @Test
    void findAll() {
        List<ProductCategory> productCategoryList = categoryService.findAll();
        Assertions.assertNotEquals(0, productCategoryList.size());
    }

    @Test
    void findByCategoryTypeIn() {
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(Arrays.asList(1, 3, 4));
        Assertions.assertNotEquals(0, productCategoryList.size());
    }

    @Test
    void save() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("小猫最爱");
        productCategory.setCategoryType(6);
        ProductCategory result = categoryService.save(productCategory);
        Assertions.assertNotNull(result);
    }
}