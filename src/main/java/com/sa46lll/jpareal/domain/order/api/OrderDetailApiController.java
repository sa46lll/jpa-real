package com.sa46lll.jpareal.domain.order.api;

import com.sa46lll.jpareal.common.dto.response.ResultResponse;
import com.sa46lll.jpareal.domain.order.dao.OrderRepository;
import com.sa46lll.jpareal.domain.order.dao.OrderSearch;
import com.sa46lll.jpareal.domain.order.dao.simplequery.OrderQueryDto;
import com.sa46lll.jpareal.domain.order.domain.Order;
import com.sa46lll.jpareal.domain.order.domain.OrderItem;
import com.sa46lll.jpareal.domain.order.dto.OrderDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderDetailApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders-detail")
    public ResultResponse<List<Order>> ordersDetailV1() { // 엔티티 직접 노출
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.forEach(o -> o.getItem().getName()); // 강제 초기화
        }
        return new ResultResponse<>(orders.size(), orders);
    }

    @GetMapping("/api/v2/orders-detail")
    public ResultResponse<List<OrderDetailDto>> ordersDetailV2() { // 엔티티 직접 노출
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDetailDto> collect = orders.stream()
                .map(OrderDetailDto::new)
                .collect(Collectors.toList());
        return new ResultResponse<>(collect.size(), collect);
    }

    @GetMapping("/api/v3/orders-detail")
    public ResultResponse<List<OrderDetailDto>> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDetailDto> collect = orders.stream()
                .map(OrderDetailDto::new)
                .collect(Collectors.toList());
        return new ResultResponse<>(collect.size(), collect);
    }

    @GetMapping("/api/v3.1/orders-detail")
    public ResultResponse<List<OrderDetailDto>> ordersV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);
        List<OrderDetailDto> collect = orders.stream()
                .map(OrderDetailDto::new)
                .collect(Collectors.toList());
        return new ResultResponse<>(collect.size(), collect);
    }
}
