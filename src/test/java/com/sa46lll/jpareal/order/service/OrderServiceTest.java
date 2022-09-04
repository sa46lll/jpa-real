package com.sa46lll.jpareal.order.service;

import com.sa46lll.jpareal.item.domain.Book;
import com.sa46lll.jpareal.item.exception.NotEnoughStockException;
import com.sa46lll.jpareal.member.domain.Address;
import com.sa46lll.jpareal.member.domain.Member;
import com.sa46lll.jpareal.order.dao.OrderRepository;
import com.sa46lll.jpareal.order.domain.Order;
import com.sa46lll.jpareal.order.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = createMember();
        Book item = createBook("JPA 정복", 10000, 10);

        // when
        int orderCount = 3;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        Order getOrder = orderRepository.findOne(orderId);

        // then
        assertEquals(OrderStatus.ORDER, getOrder.getStatus()); // 상품 주문시 상태는 ORDER
        assertEquals(1, getOrder.getOrderItems().size()); // 상품 종류 수
        assertEquals(10000 * orderCount, getOrder.getTotalPrice()); // 주문 가격은 가격 * 수량이다.
        assertEquals(7, item.getStockQuantity()); // 주문 후 재고 수량
    }

    @Test
    public void 상품주문_재고수량초과() throws NotEnoughStockException {
        // given
        Member member = createMember();
        Book item = createBook("JPA 정복", 10000, 10);
        int orderCount = 13;

        // when
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        });
    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = createMember();
        Book item = createBook("JPA 정복", 10000, 10);

        int orderCount = 3;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals(10, item.getStockQuantity()); // 주문이 취소된 상품은 재고가 원복되어야 한다.
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강남", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}