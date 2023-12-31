package com.zack.order.service;


import com.zack.common.enums.OrderEnums;
import com.zack.common.models.ItemsBean;
import com.zack.common.models.OrderBean;
import com.zack.order.entity.ItemEntity;
import com.zack.order.entity.OrderEntity;
import com.zack.order.events.handlers.EventHandler;
import com.zack.order.repository.OrderRepository;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService {
    @Resource
    private OrderRepository orderRepository;
    @Autowired
    private Mapper mapper;
    @Autowired
    private EventHandler eventHandler;

    public String createOrder(OrderBean orderBean) {

        OrderEntity orderEntity = createRequiredOrderDTOFromBean(orderBean);
        orderEntity = orderRepository.save(orderEntity);
        eventHandler.publishEvent(orderEntity);
        return  "Order created successfully !!!";
    }

    private OrderEntity createRequiredOrderDTOFromBean(OrderBean orderBean) {
        OrderEntity orderEntity = mapper.map(orderBean, OrderEntity.class);
        orderEntity.getItemEntities().clear();
        Set<ItemsBean> itemBeanSet = orderBean.getItems();
        for (ItemsBean itemsBean : itemBeanSet) {
            ItemEntity itemEntity = new ItemEntity();
            itemEntity.setItemName(itemsBean.getItemName());
            itemEntity.setOrderEntity(orderEntity);
            orderEntity.getItemEntities().add(itemEntity);
        }
        orderEntity.setStatus(OrderEnums.PENDING);
        return orderEntity;
    }

    public void updateOrderStatus(Long orderId, OrderEnums orderStatus) {
        Optional<OrderEntity> order = orderRepository.findById(orderId);
        order.ifPresent(value -> value.setStatus(orderStatus));
        order.ifPresent(value -> orderRepository.save(value));

    }

}
