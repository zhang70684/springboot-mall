package com.zhang.springbootmall.service;

import com.zhang.springbootmall.dto.CreateOrderRequest;
import com.zhang.springbootmall.dto.OrderQueryParams;
import com.zhang.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

}
