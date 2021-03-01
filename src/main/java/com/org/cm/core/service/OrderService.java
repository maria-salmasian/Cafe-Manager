package com.org.cm.core.service;

import com.org.cm.core.model.OrderModel;
import com.org.cm.core.model.ProductModel;
import com.org.cm.infrastructure.entity.Order;
import com.org.cm.infrastructure.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<OrderModel> getOrders();
    OrderModel getOrderByID(int id) ;
    Order saveOrder(OrderModel orderModel) ;
    Order updateOrderByID(int id, OrderModel orderModel) ;
    void deleteOrderByID(int id) ;
}
