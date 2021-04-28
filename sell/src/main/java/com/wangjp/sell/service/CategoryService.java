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

    // 查找单个商品类型
    ProductCategory findById(Integer categoryId);

    // 查找所有商品类型
    List<ProductCategory> findAll();

    // 根据商品类型编码 list 查询对应商品类型
    List<ProductCategory> findByCodeIn(List<String> categoryCodeList);

    // 保存单个商品类型
    ProductCategory save(ProductCategory productCategory);
}
