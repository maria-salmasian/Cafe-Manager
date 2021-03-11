package com.org.cm.core.service.impl;

import com.org.cm.core.model.OrderModel;
import com.org.cm.core.service.OrderService;
import com.org.cm.core.service.exception.CafeTableNotFound;
import com.org.cm.core.service.exception.OrderNotFoundException;
import com.org.cm.core.service.exception.ValidationException;
import com.org.cm.infrastructure.entity.CafeTable;
import com.org.cm.infrastructure.entity.Product;
import com.org.cm.infrastructure.entity.TableOrder;
import com.org.cm.infrastructure.repository.CafeTableRepository;
import com.org.cm.infrastructure.repository.OrderRepository;
import com.org.cm.infrastructure.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final
    OrderRepository orderRepository;

    private final
    CafeTableRepository cafeTableRepository;

    private final
    ProductRepository productRepository;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CafeTableRepository cafeTableRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.cafeTableRepository = cafeTableRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderModel> getOrders() {
        log.info("method getOrders is invoked");
        List<OrderModel> orderModelList = (orderRepository.findAll())
                .stream()
                .map(this::tableOrderToTableOrderModel)
                .collect(Collectors.toList());
        Assert.notEmpty(orderModelList, "No order found");
        return orderModelList;

    }

    @Override
    @Transactional(readOnly = true)
    public OrderModel getOrderByID(long id) throws OrderNotFoundException {
        log.info("method getOrderById is invoked");
        TableOrder order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("order not found for this id :: " + id));
        OrderModel orderModel =tableOrderToTableOrderModel(order);
        Assert.notNull(orderModel, "orderModel null");
        return orderModel;
    }

    @Override
    @Transactional
    public TableOrder saveOrder(OrderModel orderModel) throws CafeTableNotFound {
        log.info("method saveOrder is invoked");
        Assert.notNull(orderModel, "order model not valid");

        CafeTable cafetable = cafeTableRepository.findById(orderModel.getCafeTableId())
                .orElseThrow(() -> new CafeTableNotFound("Cafe table specified not found"));
        if (cafetable.getRemoved() == null) {
            TableOrder tableOrder = tableOrderModelToTableOrder(orderModel);
            tableOrder.setCreated(LocalDateTime.now());
            tableOrder.setUpdated(LocalDateTime.now());

            return orderRepository.save(tableOrder);

        } else throw new CafeTableNotFound("Cafe table is removed");


    }

    @Override
    @Transactional
    public TableOrder updateOrderByID(long id, OrderModel orderModel) throws OrderNotFoundException, CafeTableNotFound {
        log.info("method updateOrderById is invoked");
        Assert.notNull(orderModel, "order not valid");
        TableOrder orderToBeUpdated = orderRepository.findById(id).orElseThrow(() ->
                new OrderNotFoundException("cafe order not found for this id :: " + id));
        CafeTable cafeTable = cafeTableRepository.findById(orderModel.getCafeTableId()).orElseThrow(() -> new CafeTableNotFound("Cafe table not found"));
        if (cafeTable.getRemoved() == null) {
            TableOrder tableOrder = tableOrderModelToTableOrder(orderModel);
            orderRepository.delete(orderToBeUpdated);
            return orderRepository.save(tableOrder);
        } else throw new CafeTableNotFound("cafe table is removed");

    }

    @Override
    @Transactional
    public void deleteOrderByID(long id) throws OrderNotFoundException {
        log.info("method deleteOrderById is invoked");
        TableOrder tableOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("order not found for this id :: " + id));
        tableOrder.setRemoved(LocalDateTime.now());
        orderRepository.save(tableOrder);

    }


    private TableOrder tableOrderModelToTableOrder(OrderModel orderModel) {
        TableOrder tableOrder = new TableOrder();

        List<Product> prs = new ArrayList<>();
        orderModel.getProducts().forEach(x -> {
            Product product = productRepository.getProductById(x);
            if (product.isEnabled())
            prs.add(product);
            else {
                try {
                    throw new ValidationException("product not enables");
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
            }

        });
        tableOrder.setProducts(prs);



        tableOrder.setCafeTable(cafeTableRepository.getCafeTableById(orderModel.getCafeTableId()));
        tableOrder.setFinished(orderModel.isFinished());


        return tableOrder;
    }


    private OrderModel tableOrderToTableOrderModel(TableOrder tableOrder) {
        OrderModel orderModel = new OrderModel();
        orderModel.setFinished(tableOrder.isFinished());

        orderModel.setId(tableOrder.getId());
        orderModel.setCafeTableId(tableOrder.getCafeTable().getId());

        List<Long> prIds = new ArrayList<>();
        tableOrder.getProducts().forEach(x ->  {
            if(x!= null)
                prIds.add(x.getId());
        });
        orderModel.setProducts(prIds);

        return orderModel;
    }
}

