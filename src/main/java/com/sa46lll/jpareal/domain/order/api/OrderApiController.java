package com.sa46lll.jpareal.domain.order.api;

import com.sa46lll.jpareal.common.dto.response.ResultResponse;
import com.sa46lll.jpareal.domain.order.dao.OrderRepository;
import com.sa46lll.jpareal.domain.order.dao.OrderSearch;
import com.sa46lll.jpareal.domain.order.domain.Order;
import com.sa46lll.jpareal.domain.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        for (Order order : orders) {
            order.getMember().getName(); //Lazy 강제 초기화
            order.getDelivery().getAddress(); //Lazy 강제 초기화
        }
        return orders;
    }

    @GetMapping("/api/v2/orders")
    public ResultResponse orderV2() {
        List<OrderDto> collect = orderRepository.findAllByString(new OrderSearch()).stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());

        return new ResultResponse(collect.size(), collect);
    }
}
