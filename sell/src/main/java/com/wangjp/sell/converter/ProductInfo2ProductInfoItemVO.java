package com.wangjp.sell.converter;

import com.wangjp.sell.entity.ProductCategory;
import com.wangjp.sell.entity.ProductInfo;
import com.wangjp.sell.vo.ProductInfoItemVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/10/21 17:13
 * @detail
 */
public class ProductInfo2ProductInfoItemVO {

    public static ProductInfoItemVO convert(ProductInfo productInfo, List<ProductCategory> productCategoryList) {
        ProductInfoItemVO productInfoItemVO = new ProductInfoItemVO();
        BeanUtils.copyProperties(productInfo, productInfoItemVO);
        String categoryName = "";
        for (ProductCategory category : productCategoryList) {
            if (category.getCode().equals(productInfo.getCategoryCode())) {
                categoryName = category.getName();
            }
        }
        productInfoItemVO.setCategoryName(categoryName);
        return productInfoItemVO;
    }

    public static List<ProductInfoItemVO> convert(List<ProductInfo> productInfoList, List<ProductCategory> productCategoryList) {
        return productInfoList.stream().map(productInfo -> ProductInfo2ProductInfoItemVO.convert(productInfo, productCategoryList)).collect(Collectors.toList());
    }
}
