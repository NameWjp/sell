package com.wangjp.sell.repository;

import com.wangjp.sell.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/1/27 10:31 下午
 * @detail 商品类目映射
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    List<ProductCategory> findByCodeIn(List<String> categoryCodeList);
}
