package com.webshop.orders.facade;

import com.webshop.orders.dto.DetailedOrderDto;
import com.webshop.orders.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderFacade {

    void addOrder(OrderDto orderDto);

    Page<DetailedOrderDto> findAllOrders(Pageable pageable);

}
