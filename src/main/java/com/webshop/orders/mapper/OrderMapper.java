package com.webshop.orders.mapper;

import com.webshop.orders.domain.Order;
import com.webshop.orders.domain.OrderStatus;
import com.webshop.orders.domain.Product;
import com.webshop.orders.domain.User;
import com.webshop.orders.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class OrderMapper {

    public Order orderDtoToOrder(OrderDto orderDto, OrderStatus orderStatus) {
        return Order.builder()
                .status(orderStatus)
                .products(orderDtoProductListToOrderProductList(orderDto.getProducts()))
                .user(orderDtoUserToUser(orderDto.getUser()))
                .build();
    }

    private Product orderDtoProductToProduct(OrderDto.Product orderDtoProduct) {
        if (orderDtoProduct == null) {
            return null;
        }
        return new Product(orderDtoProduct.getId(), orderDtoProduct.getNumber());
    }

    private List<Product> orderDtoProductListToOrderProductList(List<OrderDto.Product> orderDtoProductList) {
        if (orderDtoProductList == null) {
            return null;
        }
        return orderDtoProductList.stream()
                .map(this::orderDtoProductToProduct)
                .collect(Collectors.toList());
    }

    private User orderDtoUserToUser(OrderDto.User orderDtoUser) {
        if (orderDtoUser == null) {
            return null;
        }
        return new User(orderDtoUser.getId());
    }

}
