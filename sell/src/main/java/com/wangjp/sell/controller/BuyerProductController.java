package com.wangjp.sell.controller;

import com.wangjp.sell.vo.ProductInfoVO;
import com.wangjp.sell.vo.ProductVO;
import com.wangjp.sell.vo.ResultVO;
import com.wangjp.sell.entity.ProductCategory;
import com.wangjp.sell.entity.ProductInfo;
import com.wangjp.sell.service.CategoryService;
import com.wangjp.sell.service.ProductService;
import com.wangjp.sell.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/2/27 3:45 下午
 * @detail 买家商品
 */
@Api(tags = "买家商品")
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("获取所有商品")
    @GetMapping("/list")
    public ResultVO<List<ProductVO>> list() {
        // 查询所有上架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        // 查询商品的类目
        List<String> categoryTypeList = productInfoList.stream().map(ProductInfo::getCategoryCode).distinct().collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCodeIn(categoryTypeList);

        // 数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryCode(productCategory.getCode());
            productVO.setCategoryName(productCategory.getName());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productCategory.getCode().equals(productInfo.getCategoryCode())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }

            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }
}
