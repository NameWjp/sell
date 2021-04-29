package com.wangjp.sell.converter;

import com.wangjp.sell.dto.CartDTO;
import com.wangjp.sell.dto.OrderDTO;
import com.wangjp.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/24 4:31 下午
 * @detail
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO<CartDTO> convert(OrderForm orderForm) {
        OrderDTO<CartDTO> orderDTO = new OrderDTO<>();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setDetailList(orderForm.getCartList());

        return orderDTO;
    }
}
