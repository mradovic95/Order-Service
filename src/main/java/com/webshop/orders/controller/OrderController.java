package com.webshop.orders.controller;

import com.webshop.orders.dto.DetailedOrderDto;
import com.webshop.orders.facade.OrderFacade;
import com.webshop.orders.secutiry.CheckSecurity;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @ApiOperation(value = "Get all orders")
    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Page<DetailedOrderDto>> getAllProducts(@RequestHeader("Authorization") String authorization, Pageable pageable) {
        return new ResponseEntity<>(orderFacade.findAllOrders(pageable), HttpStatus.OK);
    }

}
