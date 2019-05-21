package com.webshop.orders.service.impl;

import com.webshop.orders.domain.Order;
import com.webshop.orders.exception.NotFoundException;
import com.webshop.orders.repository.OrderRepository;
import com.webshop.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order findById(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                "Order with id: %s not found", id)));
    }

    @Override
    public Order add(Order order) {
        return orderRepository.insert(order);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

}
