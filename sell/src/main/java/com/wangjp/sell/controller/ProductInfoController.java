package com.wangjp.sell.controller;

import com.wangjp.sell.converter.Page2PaginationVOConverter;
import com.wangjp.sell.converter.ProductInfo2ProductInfoItemVO;
import com.wangjp.sell.entity.ProductCategory;
import com.wangjp.sell.entity.ProductInfo;
import com.wangjp.sell.enums.ResultEnum;
import com.wangjp.sell.exception.SellException;
import com.wangjp.sell.form.ProductInfoForm;
import com.wangjp.sell.groups.Update;
import com.wangjp.sell.service.CategoryService;
import com.wangjp.sell.service.ProductService;
import com.wangjp.sell.utils.KeyUtil;
import com.wangjp.sell.utils.ResultVOUtil;
import com.wangjp.sell.vo.PaginationVO;
import com.wangjp.sell.vo.ProductInfoItemVO;
import com.wangjp.sell.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/10/19 10:06
 * @detail
 */
@Slf4j
@Api(tags = "商品管理")
@RestController
@RequestMapping("/productInfo")
public class ProductInfoController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @ApiOperation("创建商品")
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('productInfo:create')")
    public ResultVO<Object> create(@RequestBody @Validated ProductInfoForm productInfoForm) {
        ProductCategory productCategory = categoryService.findByCode(productInfoForm.getCategoryCode());

        if (productCategory == null) {
            throw new SellException(ResultEnum.CATEGORY_NOT_EXIST);
        }

        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(KeyUtil.genUniqueKey());

        setProductInfoByProductInfoForm(productInfo, productInfoForm);

        productService.save(productInfo);

        return ResultVOUtil.success();
    }

    @ApiOperation("修改商品")
    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('productInfo:update')")
    public ResultVO<Object> update(@PathVariable("id") String id, @RequestBody @Validated(Update.class) ProductInfoForm productInfoForm) {
        ProductInfo productInfo = productService.findById(id);

        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_INFO_NOT_EXIST);
        }

        setProductInfoByProductInfoForm(productInfo, productInfoForm);

        productService.save(productInfo);

        return ResultVOUtil.success();
    }

    private void setProductInfoByProductInfoForm(ProductInfo productInfo, ProductInfoForm productInfoForm) {
        productInfo.setProductName(productInfoForm.getProductName());
        productInfo.setProductPrice(productInfoForm.getProductPrice());
        productInfo.setProductStock(productInfoForm.getProductStock());
        productInfo.setProductDescription(productInfoForm.getProductDescription());
        productInfo.setProductIcon(productInfoForm.getProductIcon());
        productInfo.setProductStatus(productInfoForm.getProductStatus());
        productInfo.setCategoryCode(productInfoForm.getCategoryCode());
    }

    @ApiOperation("删除商品")
    @PostMapping("/delete")
    @PreAuthorize("hasAnyAuthority('productInfo:delete')")
    public ResultVO<Object> delete(@RequestBody List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new SellException(ResultEnum.PRODUCT_INFO_ID_NOT_EMPTY);
        }

        for (String id : ids) {
            ProductInfo productInfo = productService.findById(id);
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_INFO_NOT_EXIST);
            }
        }

        productService.deleteProductInfoWithIds(ids);

        return ResultVOUtil.success();
    }

    @ApiOperation("查询商品")
    @GetMapping("/getInfo/{id}")
    public ResultVO<ProductInfo> getCategoryInfo(@PathVariable("id") String id) {
        ProductInfo productInfo = productService.findById(id);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_INFO_NOT_EXIST);
        }
        return ResultVOUtil.success(productInfo);
    }

    @ApiOperation("获取商品列表")
    @GetMapping("/list")
    public ResultVO<PaginationVO<ProductInfoItemVO>> list(
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNum,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String categoryCode
    ) {

        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));

        Specification<ProductInfo> specification = new Specification<ProductInfo>() {
            @Override
            public Predicate toPredicate(Root<ProductInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(productName)) {
                    list.add(criteriaBuilder.like(root.get("productName"), "%" + productName + "%"));
                }
                if (!StringUtils.isEmpty(categoryCode)) {
                    list.add(criteriaBuilder.equal(root.get("categoryCode"), categoryCode));
                }

                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };

        Page<ProductInfo> productInfoPage = productService.findAll(specification, pageRequest);
        List<String> categoryCodes = productInfoPage.getContent().stream().map(ProductInfo::getCategoryCode).collect(Collectors.toList());
        List<ProductCategory> categoryList = categoryService.findByCodeIn(categoryCodes);
        Page<ProductInfoItemVO> productInfoItemVOPage = productInfoPage.map(productInfo -> ProductInfo2ProductInfoItemVO.convert(productInfo, categoryList));

        return ResultVOUtil.success(Page2PaginationVOConverter.convert(productInfoItemVOPage));
    }
}
