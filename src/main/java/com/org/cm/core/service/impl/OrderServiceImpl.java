package com.org.cm.core.service.impl;

import com.org.cm.core.model.OrderModel;
import com.org.cm.core.service.OrderService;
import com.org.cm.core.service.exception.CafeTableNotFound;
import com.org.cm.core.service.exception.OrderNotFoundException;
import com.org.cm.core.service.exception.ProductNotFound;
import com.org.cm.infrastructure.entity.CafeTable;
import com.org.cm.infrastructure.entity.Product;
import com.org.cm.infrastructure.entity.TableOrder;
import com.org.cm.infrastructure.repository.CafeTableRepository;
import com.org.cm.infrastructure.repository.OrderRepository;
import com.org.cm.infrastructure.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    private final
    ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, CafeTableRepository cafeTableRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.cafeTableRepository = cafeTableRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderModel> getOrders() {
        log.info("method getOrders is invoked");
        List<OrderModel> orderModelList = (orderRepository.findAll())
                .stream()
                .map(x -> modelMapper.map(x, OrderModel.class))
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
        OrderModel orderModel = modelMapper.map(order, OrderModel.class);
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
            TableOrder tableOrder = modelMapper.map(orderModel, TableOrder.class);
            tableOrder.setCreated(LocalDateTime.now());
            tableOrder.setUpdated(LocalDateTime.now());

            List<Product> products = new ArrayList<>();
            orderModel.getProducts().forEach(x-> {
                try {
                    products.add(productRepository.findById(x).orElseThrow(() ->
                            new ProductNotFound("product not found for this id :: "+x)) );
                } catch (ProductNotFound productNotFound) {
                    productNotFound.printStackTrace();
                }
            });

            tableOrder.setProducts(products);

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
        TableOrder tableOrder = modelMapper.map(orderModel, TableOrder.class);
        CafeTable cafeTable = cafeTableRepository.findById(orderModel.getCafeTableId()).orElseThrow(() -> new CafeTableNotFound("Cafe table not found"));
        if (cafeTable.getRemoved() == null) {
            tableOrder.setCafeTable(cafeTable);
            tableOrder.setCreated(orderToBeUpdated.getCreated());
            tableOrder.setUpdated(LocalDateTime.now());

            List<Product> products = new ArrayList<>();
            orderModel.getProducts().forEach(x-> {
                try {
                    products.add(productRepository.findById(x).orElseThrow(() ->
                            new ProductNotFound("product not found for this id :: "+x)) );
                } catch (ProductNotFound productNotFound) {
                    productNotFound.printStackTrace();
                }
            });

            tableOrder.setProducts(products);

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
}
