package com.wangjp.sell.controller;

import com.wangjp.sell.converter.Page2PaginationVOConverter;
import com.wangjp.sell.entity.ProductCategory;
import com.wangjp.sell.entity.ProductInfo;
import com.wangjp.sell.enums.CategorySortEnum;
import com.wangjp.sell.enums.ResultEnum;
import com.wangjp.sell.exception.SellException;
import com.wangjp.sell.form.CategoryForm;
import com.wangjp.sell.groups.Update;
import com.wangjp.sell.service.CategoryService;
import com.wangjp.sell.service.ProductService;
import com.wangjp.sell.utils.ResultVOUtil;
import com.wangjp.sell.vo.PaginationVO;
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

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/10/17 11:00 上午
 * @detail
 */
@Slf4j
@Api(tags = "商品类型管理")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @ApiOperation("创建商品类型")
    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('category:create')")
    public ResultVO<Object> create(@RequestBody @Validated CategoryForm categoryForm) {
        ProductCategory productCategory = new ProductCategory();

        productCategory.setName(categoryForm.getName());
        productCategory.setCode(categoryForm.getCode());
        productCategory.setSort(categoryForm.getSort());

        categoryService.save(productCategory);

        return ResultVOUtil.success();
    }

    @ApiOperation("修改商品类型")
    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('category:update')")
    public ResultVO<Object> update(@PathVariable("id") Integer id, @RequestBody @Validated(Update.class) CategoryForm categoryForm) {
        ProductCategory productCategory = categoryService.findById(id);

        if (productCategory == null) {
            throw new SellException(ResultEnum.CATEGORY_NOT_EXIST);
        }

        productCategory.setName(categoryForm.getName());
        productCategory.setCode(categoryForm.getCode());
        productCategory.setSort(categoryForm.getSort());

        categoryService.save(productCategory);

        return ResultVOUtil.success();
    }

    @ApiOperation("删除商品类型")
    @PostMapping("/delete")
    @PreAuthorize("hasAnyAuthority('category:delete')")
    public ResultVO<Object> delete(@RequestBody List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new SellException(ResultEnum.CATEGORY_ID_NOT_EMPTY);
        }
        for (Integer id : ids) {
            ProductCategory productCategory = categoryService.findById(id);
            if (productCategory == null) {
                throw new SellException(ResultEnum.CATEGORY_NOT_EXIST);
            }
            List<ProductInfo> productInfoList = productService.findByCategoryCode(productCategory.getCode());
            if (!CollectionUtils.isEmpty(productInfoList)) {
                throw new SellException(ResultEnum.CATEGORY_DELETE_EXIST_PRODUCT_INFO);
            }
        }


        categoryService.deleteProductCategoryWithIds(ids);

        return ResultVOUtil.success();
    }

    @ApiOperation("查询商品类型")
    @GetMapping("/getInfo/{id}")
    public ResultVO<ProductCategory> getCategoryInfo(@PathVariable("id") Integer id) {
        ProductCategory category = categoryService.findById(id);
        if (category == null) {
            throw new SellException(ResultEnum.CATEGORY_NOT_EXIST);
        }
        return ResultVOUtil.success(category);
    }

    @ApiOperation("获取商品类型列表")
    @GetMapping("/list")
    public ResultVO<PaginationVO<ProductCategory>> list(
            @RequestParam Integer pageSize,
            @RequestParam Integer pageNum,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) Integer sort
    ) {
        Sort pageSort;

        if (CategorySortEnum.ASC.getCode().equals(sort)) {
            pageSort = Sort.by(Sort.Direction.ASC, "sort");
        } else if (CategorySortEnum.DESC.getCode().equals(sort)){
            pageSort = Sort.by(Sort.Direction.DESC, "sort");
        } else {
            pageSort = Sort.by(Sort.Direction.DESC, "id");
        }

        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, pageSort);

        Specification<ProductCategory> specification = new Specification<ProductCategory>() {
            @Override
            public Predicate toPredicate(Root<ProductCategory> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                if (!StringUtils.isEmpty(name)) {
                    list.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
                }
                if (!StringUtils.isEmpty(code)) {
                    list.add(criteriaBuilder.like(root.get("code"), "%" + code + "%"));
                }

                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };

        Page<ProductCategory> productCategoryPage = categoryService.findAll(specification, pageRequest);

        return ResultVOUtil.success(Page2PaginationVOConverter.convert(productCategoryPage));
    }
}
