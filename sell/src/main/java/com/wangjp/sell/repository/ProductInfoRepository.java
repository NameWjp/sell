package com.wangjp.sell.repository;

import com.wangjp.sell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/2/24 9:13 下午
 * @detail 商品详情映射
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String>, JpaSpecificationExecutor<ProductInfo> {

    List<ProductInfo> findByProductStatus(Integer productStatus);

    List<ProductInfo> findByCategoryCode(String categoryCode);

    @Transactional
    @Modifying
    @Query("delete from ProductInfo where id in ?1")
    void deleteProductInfoWithIds(List<String> ids);
}
