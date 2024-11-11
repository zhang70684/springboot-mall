package com.zhang.springbootmall.service;

import com.zhang.springbootmall.dto.CreateOrderRequest;
import com.zhang.springbootmall.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
