package com.sa46lll.jpareal.domain.order.service;

import com.sa46lll.jpareal.domain.item.dao.ItemRepository;
import com.sa46lll.jpareal.domain.item.domain.Item;
import com.sa46lll.jpareal.domain.member.dao.MemberRepository;
import com.sa46lll.jpareal.domain.order.dao.OrderRepository;
import com.sa46lll.jpareal.domain.order.dao.OrderSearch;
import com.sa46lll.jpareal.domain.order.domain.Delivery;
import com.sa46lll.jpareal.domain.order.domain.OrderItem;
import com.sa46lll.jpareal.domain.member.domain.Member;
import com.sa46lll.jpareal.domain.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);
        // 주문 저장장
       orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
        // jpa 의 변경 감지를 통해 변경된 부분의 업데이트 쿼리를 자동으로 날려줌.
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
