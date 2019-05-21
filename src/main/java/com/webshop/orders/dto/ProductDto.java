package com.webshop.orders.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private String id;
    private String title;
    private String description;
    private BigDecimal price;

}
