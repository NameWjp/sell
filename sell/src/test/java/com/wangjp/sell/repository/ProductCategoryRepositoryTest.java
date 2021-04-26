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
        productCategory.setName("男士最爱");
        productCategory.setCode("men_love");
        productCategory.setSort(10);

        ProductCategory result = repository.save(productCategory);
        Assertions.assertNotNull(result);
    }

    @Test
    public void updateTest() {
        ProductCategory productCategory = repository.findById(5).orElse(null);
        if (productCategory != null) {
            productCategory.setName("小朋友的最爱");
            ProductCategory result = repository.save(productCategory);
            Assertions.assertNotNull(result);
        }
    }

    @Test
    public void deleteTest() {
        repository.deleteById(5);
    }

    @Test
    public void findByTypeInTest() {
        List<String> list = Arrays.asList("1", "3", "4");
        List<ProductCategory> result = repository.findByCodeIn(list);
        Assertions.assertNotEquals(0, result.size());
    }
}