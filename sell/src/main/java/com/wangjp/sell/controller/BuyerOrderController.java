package com.wangjp.sell.controller;

import com.wangjp.sell.converter.OrderCreateForm2OrderDTOConverter;
import com.wangjp.sell.dto.CartDTO;
import com.wangjp.sell.dto.OrderDTO;
import com.wangjp.sell.enums.ResultEnum;
import com.wangjp.sell.exception.SellException;
import com.wangjp.sell.form.OrderCreateForm;
import com.wangjp.sell.service.OrderService;
import com.wangjp.sell.utils.ResultVOUtil;
import com.wangjp.sell.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/24 3:19 下午
 * @detail
 */
@Api(tags = "买家订单")
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("创建订单")
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@RequestBody @Validated OrderCreateForm createForm) {
        if (CollectionUtils.isEmpty(createForm.getCartList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO<CartDTO> orderDTO = OrderCreateForm2OrderDTOConverter.convert(createForm);
        OrderDTO<CartDTO> createResult = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getId());

        return ResultVOUtil.success(map);
    }

    // 订单列表

    // 订单详情
}
