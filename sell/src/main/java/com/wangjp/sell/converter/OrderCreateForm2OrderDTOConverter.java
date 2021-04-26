package com.wangjp.sell.converter;

import com.wangjp.sell.dto.CartDTO;
import com.wangjp.sell.dto.OrderDTO;
import com.wangjp.sell.form.OrderCreateForm;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/24 4:31 下午
 * @detail
 */
@Slf4j
public class OrderCreateForm2OrderDTOConverter {

    public static OrderDTO<CartDTO> convert(OrderCreateForm createForm) {
        OrderDTO<CartDTO> orderDTO = new OrderDTO<>();

        orderDTO.setBuyerName(createForm.getName());
        orderDTO.setBuyerPhone(createForm.getPhone());
        orderDTO.setBuyerAddress(createForm.getAddress());
        orderDTO.setBuyerOpenid(createForm.getOpenid());
        orderDTO.setDetailList(createForm.getCartList());

        return orderDTO;
    }
}
