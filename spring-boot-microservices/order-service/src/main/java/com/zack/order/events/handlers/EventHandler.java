package com.zack.order.events.handlers;

import com.zack.common.enums.OrderEnums;
import com.zack.common.models.OrderEventBean;
import com.zack.order.entity.OrderEntity;
import com.zack.order.events.CustomerEventSource;
import com.zack.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Component
@EnableBinding(CustomerEventSource.class)
public class EventHandler {
    @Autowired
    OrderService orderService;

    @Autowired
    CustomerEventSource customerEventSource;

    public void publishEvent(OrderEntity orderEntityBean) {
        OrderEventBean model = new OrderEventBean();
        model.setCustomerId(orderEntityBean.getCustomerId());
        model.setOrderId(orderEntityBean.getOrderId());
        model.setAction(OrderEnums.CREATE);
        customerEventSource.CustomerEventsChannel().send(MessageBuilder.withPayload(model).build());

    }

    public String updateEvent(OrderEventBean orderBean) {
        orderService.updateOrderStatus(orderBean.getOrderId(), OrderEnums.PLACED);
        return "Order Placed successfully ";
    }
}
