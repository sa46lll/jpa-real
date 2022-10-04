package com.sa46lll.jpareal.domain.order.dto;

import com.sa46lll.jpareal.domain.member.domain.Address;
import com.sa46lll.jpareal.domain.order.domain.Order;
import com.sa46lll.jpareal.domain.order.domain.OrderItem;
import com.sa46lll.jpareal.domain.order.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDetailDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDetailDto(Order order) {
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();
        this.address = order.getDelivery().getAddress();
        this.orderItems = order.getOrderItems().stream()
                .map(OrderItemDto::new)
                .collect(Collectors.toList());
    }
}
