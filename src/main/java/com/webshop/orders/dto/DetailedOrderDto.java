package com.webshop.orders.dto;

import com.webshop.orders.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailedOrderDto {

    private String id;
    private OrderStatus orderStatus;
    private List<DetailedOrderProductDto> products;
    private UserDto user;

}
