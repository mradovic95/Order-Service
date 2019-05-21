package com.webshop.orders.listener;

import com.webshop.orders.dto.OrderDto;
import com.webshop.orders.facade.OrderFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderListener extends MessageSupport {

    private final OrderFacade orderFacade;

    @JmsListener(destination = "${destination.order}", concurrency = "5-10")
    public void addOrder(Message message) {
        OrderDto orderDto = getMessage(message, OrderDto.class);
        orderFacade.addOrder(orderDto);
    }

}
