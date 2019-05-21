package com.webshop.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailedOrderProductDto {
    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    private Integer number;
}
