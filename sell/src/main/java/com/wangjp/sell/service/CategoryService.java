package com.wangjp.sell.service;

import com.wangjp.sell.entity.ProductCategory;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/2/24 8:27 下午
 * @detail
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
