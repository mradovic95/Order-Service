package com.webshop.orders.service;

import com.webshop.orders.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    Page<Order> findAll(Pageable pageable);

    Order findById(String id);

    Order add(Order order);

    Order update(Order order);

}
