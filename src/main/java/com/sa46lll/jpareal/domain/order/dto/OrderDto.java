package com.sa46lll.jpareal.domain.order.dto;

import com.sa46lll.jpareal.domain.order.domain.Order;
import com.sa46lll.jpareal.domain.order.domain.OrderStatus;
import com.sa46lll.jpareal.domain.member.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderDto(Order order) {
        this.orderId = order.getId();
        this.name = order.getMember().getName();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();
        this.address = order.getDelivery().getAddress();
    }
}
