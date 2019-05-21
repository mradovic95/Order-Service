package com.webshop.orders.facade.impl;

import com.webshop.orders.domain.Order;
import com.webshop.orders.domain.OrderStatus;
import com.webshop.orders.domain.Product;
import com.webshop.orders.dto.DetailedOrderDto;
import com.webshop.orders.dto.DetailedOrderProductDto;
import com.webshop.orders.dto.OrderDto;
import com.webshop.orders.dto.ProductDto;
import com.webshop.orders.dto.ProductsByIdsRequestDto;
import com.webshop.orders.dto.UserDto;
import com.webshop.orders.facade.OrderFacade;
import com.webshop.orders.mapper.OrderMapper;
import com.webshop.orders.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class OrderFacadeImpl implements OrderFacade {

    private OrderService orderService;
    private OrderMapper orderMapper;
    private RestTemplate restTemplate;
    private String productsServiceUrl;
    private String userServiceUrl;

    public OrderFacadeImpl(OrderService orderService, OrderMapper orderMapper, RestTemplate restTemplate,
                           @Value("${webshop.productsService.url}") String productsServiceUrl,
                           @Value("${webshop.usersService.url}") String userServiceUrl) {

        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.restTemplate = restTemplate;
        this.productsServiceUrl = productsServiceUrl;
        this.userServiceUrl = userServiceUrl;
    }

    @Override
    public void addOrder(OrderDto orderDto) {
        orderService.add(orderMapper.orderDtoToOrder(orderDto, OrderStatus.NOT_DELIVERED));
    }

    @Override
    public Page<DetailedOrderDto> findAllOrders(Pageable pageable) {
        //Orders
        Page<Order> orders = orderService.findAll(pageable);
        return orders.map(order -> {
            DetailedOrderDto orderDto = new DetailedOrderDto();
            orderDto.setId(order.getId());
            orderDto.setOrderStatus(order.getStatus());
            orderDto.setUser(getUser(order.getUser().getId()));
            orderDto.setProducts(getProducts(order.getProducts()).stream()
                    .map(productDto -> DetailedOrderProductDto.builder()
                            .id(productDto.getId())
                            .title(productDto.getTitle())
                            .description(productDto.getDescription())
                            .price(productDto.getPrice())
                            .number(numberOfProducts(productDto.getId(), order.getProducts()))
                            .build())
                    .collect(Collectors.toList()));
            return orderDto;
        });
    }

    private UserDto getUser(Long id) {
        ResponseEntity<UserDto> user = restTemplate.getForEntity(String.format("%s/users/%d", userServiceUrl, id), UserDto.class);
        if (user.getStatusCode().equals(HttpStatus.OK)) {
            return user.getBody();
        }
        throw new RuntimeException("Error in communication with remote service.");
    }

    private List<ProductDto> getProducts(List<Product> products) {
        ProductsByIdsRequestDto productsByIdsRequestDto = new ProductsByIdsRequestDto();
        products.forEach((product) -> productsByIdsRequestDto.getProducts().add(new ProductsByIdsRequestDto.Product(product.getId())));

        ResponseEntity<ProductDto[]> productsDtos = restTemplate.postForEntity(String.format("%s/products/byIds", productsServiceUrl), productsByIdsRequestDto, ProductDto[].class);
        if (productsDtos.getStatusCode().equals(HttpStatus.OK)) {
            return Arrays.asList(productsDtos.getBody());
        }
        throw new RuntimeException("Error in communication with remote service.");
    }

    private Integer numberOfProducts(String id, List<Product> products) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .get()
                .getNumber();
    }

}
