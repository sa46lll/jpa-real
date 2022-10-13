package com.sa46lll.jpareal.item.service;

import com.sa46lll.jpareal.item.dao.ItemRepository;
import com.sa46lll.jpareal.item.domain.Book;
import com.sa46lll.jpareal.item.domain.Item;
import com.sa46lll.jpareal.item.dto.UpdateItemDto;
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
    public void changeBook(Long itemId, UpdateItemDto updateItemDto) {
        Book book = (Book) itemRepository.findOne(itemId);
        book.changeBook(updateItemDto);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
