package com.sa46lll.jpareal.domain.item.service;

import com.sa46lll.jpareal.domain.item.dao.ItemRepository;
import com.sa46lll.jpareal.domain.item.domain.Book;
import com.sa46lll.jpareal.domain.item.domain.Item;
import com.sa46lll.jpareal.domain.item.dto.UpdateItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void changeBook(Long itemId, UpdateItemRequest updateItemRequest) {
        Book book = (Book) itemRepository.findOne(itemId);
        book.changeBook(updateItemRequest);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
