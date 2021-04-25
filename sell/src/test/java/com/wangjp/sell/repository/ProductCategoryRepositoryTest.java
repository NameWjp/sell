package com.wangjp.sell.repository;

import com.wangjp.sell.entity.ProductCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest() {
        repository.findById(1).ifPresent(productCategory -> System.out.println(productCategory.toString()));
    }

    @Test
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("男士最爱");
        productCategory.setCategoryType(4);
        repository.save(productCategory);
    }

    @Test
    public void updateTest() {
        ProductCategory productCategory = repository.findById(5).orElse(null);
        if (productCategory != null) {
            productCategory.setCategoryName("小朋友的最爱");
            repository.save(productCategory);
        }
    }

    @Test
    public void deleteTest() {
        repository.deleteById(5);
    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> list = Arrays.asList(1, 3, 4);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assertions.assertNotEquals(0, result.size());
    }
}