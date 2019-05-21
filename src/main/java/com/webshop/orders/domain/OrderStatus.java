package com.webshop.orders.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum OrderStatus {
    NOT_DELIVERED,
    IN_DELIVERY_PROCESS,
    DELIVERED;
}
