package com.wangjp.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wangjp.sell.dto.OrderDTO;
import com.wangjp.sell.entity.OrderDetail;
import com.wangjp.sell.enums.ResultEnum;
import com.wangjp.sell.exception.SellException;
import com.wangjp.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/24 4:31 下午
 * @detail
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderDTO.getBuyerPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误，string={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAMS_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
