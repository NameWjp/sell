package com.wangjp.sell.service.impl;

import com.wangjp.sell.converter.OrderMaster2OrderDTOConverter;
import com.wangjp.sell.dto.CartDTO;
import com.wangjp.sell.dto.OrderDTO;
import com.wangjp.sell.entity.OrderDetail;
import com.wangjp.sell.entity.OrderMaster;
import com.wangjp.sell.entity.ProductInfo;
import com.wangjp.sell.enums.OrderStatusEnum;
import com.wangjp.sell.enums.PayStatusEnum;
import com.wangjp.sell.enums.ResultEnum;
import com.wangjp.sell.exception.SellException;
import com.wangjp.sell.repository.OrderDetailRepository;
import com.wangjp.sell.repository.OrderMasterRepository;
import com.wangjp.sell.service.OrderService;
import com.wangjp.sell.service.ProductService;
import com.wangjp.sell.utils.CopyUtil;
import com.wangjp.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/3/4 8:57 下午
 * @detail
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        String orderId = KeyUtil.genUniqueKey();

        // 查询商品的数量，价格
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            Integer quantity = orderDetail.getProductQuantity();
            String productId = orderDetail.getProductId();

            ProductInfo productInfo = productService.findOne(productId);
            // 判断商品是否存在
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            // 计算总价
            orderAmount = orderAmount.add(productInfo.getProductPrice().multiply(new BigDecimal(quantity)));

            // 订单详情入库
            BeanUtils.copyProperties(productInfo, orderDetail, CopyUtil.getIgnoreKey());
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetailRepository.save(orderDetail);
        }

        // 写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster, CopyUtil.getIgnoreKey());
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMasterRepository.save(orderMaster);

        // 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderMaster cancel(OrderDTO orderDTO) {
        // 判断传入订单 id 是否存在
        OrderMaster orderMaster = orderMasterRepository.findById(orderDTO.getOrderId()).orElse(null);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderMaster.getOrderId());

        // 判断订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态不正确，orderId={}, orderStatus={}", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        orderMasterRepository.save(orderMaster);

        // 返还库存
        if (CollectionUtils.isEmpty(orderDetailList)) {
            log.error("【取消订单】订单中无商品详情，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        List<CartDTO> cartDTOList = orderDetailList.stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        // 如果已支付，需要退款
        if (orderMaster.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            // TODO
        }
        return orderMaster;
    }

    @Override
    @Transactional
    public OrderMaster finish(OrderDTO orderDTO) {
        // 判断传入订单 id 是否存在
        OrderMaster orderMaster = orderMasterRepository.findById(orderDTO.getOrderId()).orElse(null);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 判断订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确，orderId={}, orderStatus={}", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterRepository.save(orderMaster);
        return orderMaster;
    }

    @Override
    @Transactional
    public OrderMaster paid(OrderDTO orderDTO) {
        // 判断传入订单 id 是否存在
        OrderMaster orderMaster = orderMasterRepository.findById(orderDTO.getOrderId()).orElse(null);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 判断订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付】订单状态不正确，orderId={}, orderStatus={}", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 判断支付状态
        if (!orderMaster.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付】订单支付状态不正确，orderId={}, orderStatus={}", orderMaster.getOrderId(), orderMaster.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        // 修改支付状态
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        orderMasterRepository.save(orderMaster);
        return orderMaster;
    }
}
