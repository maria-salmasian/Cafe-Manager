package com.org.cm.ws.controller;

import com.org.cm.core.model.OrderModel;
import com.org.cm.core.service.OrderService;
import com.org.cm.core.service.exception.CafeNotFoundException;
import com.org.cm.core.service.exception.CafeTableNotFound;
import com.org.cm.core.service.exception.OrderNotFoundException;
import com.org.cm.ws.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/tableOrder")
public class OrderController {
    private final
    OrderService orderService;
    private final
    ModelMapper modelMapper;

    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") long id) throws OrderNotFoundException {
        log.info("method getOrderById in Controller is invoked");

        OrderModel orderModel = orderService.getOrderByID(id);
        OrderDTO orderDTO = modelMapper.map(orderModel, OrderDTO.class);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders() {
        log.info("method getOrders in Controller is invoked");

        List<OrderModel> orderModelList = orderService.getOrders();
        List<OrderDTO> orderDTOS = orderModelList.stream().map(x -> modelMapper.map(x, OrderDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createOrder(@Valid @RequestBody OrderDTO orderDTO) throws CafeTableNotFound, CafeNotFoundException {
        log.info("method createOrder in Controller is invoked");

        OrderModel orderModel = modelMapper.map(orderDTO, OrderModel.class);
        orderService.saveOrder(orderModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable("id") long id,
                                                     @Valid @RequestBody OrderDTO orderDTO) throws OrderNotFoundException, CafeTableNotFound {
        log.info("method updateOrder in Controller is invoked");

        OrderModel orderModel = modelMapper.map(orderDTO, OrderModel.class);
        orderService.updateOrderByID(id, orderModel);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(
            @PathVariable("id") long id) throws OrderNotFoundException {
        log.info("method deleteOrder in Controller is invoked");

        orderService.deleteOrderByID(id);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
