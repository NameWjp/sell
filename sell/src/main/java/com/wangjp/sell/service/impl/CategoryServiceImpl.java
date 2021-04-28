package com.wangjp.sell.service.impl;

import com.wangjp.sell.entity.ProductCategory;
import com.wangjp.sell.repository.ProductCategoryRepository;
import com.wangjp.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/2/24 8:37 下午
 * @detail 类目 service
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCodeIn(List<String> categoryCodeList) {
        return repository.findByCodeIn(categoryCodeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
