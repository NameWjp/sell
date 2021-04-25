package com.wangjp.sell.service;

import com.wangjp.sell.dto.CartDTO;
import com.wangjp.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/2/25 8:40 下午
 * @detail
 */
public interface ProductService {

    ProductInfo findOne(String productId);

    // 查询所有在家商品列表
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    // 加库存
    void increaseStock(List<CartDTO> cartDTOList);

    // 减库存
    void decreaseStock(List<CartDTO> cartDTOList);
}
