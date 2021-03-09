package com.org.cm.core.service;

import com.org.cm.core.model.OrderModel;
import com.org.cm.core.service.exception.CafeNotFoundException;
import com.org.cm.core.service.exception.CafeTableNotFound;
import com.org.cm.core.service.exception.OrderNotFoundException;
import com.org.cm.infrastructure.entity.TableOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<OrderModel> getOrders();
    OrderModel getOrderByID(long id) throws OrderNotFoundException;
    TableOrder saveOrder(OrderModel orderModel) throws CafeNotFoundException, CafeTableNotFound;
    TableOrder updateOrderByID(long id, OrderModel orderModel) throws OrderNotFoundException, CafeTableNotFound;
    void deleteOrderByID(long id) throws OrderNotFoundException;
}
