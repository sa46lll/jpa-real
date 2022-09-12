package com.sa46lll.jpareal.order.dao;

import com.sa46lll.jpareal.order.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
