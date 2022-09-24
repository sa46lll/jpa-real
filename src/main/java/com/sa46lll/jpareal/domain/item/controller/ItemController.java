package com.sa46lll.jpareal.domain.item.controller;

import com.sa46lll.jpareal.domain.item.domain.Book;
import com.sa46lll.jpareal.domain.item.domain.Item;
import com.sa46lll.jpareal.domain.item.dto.UpdateItemRequest;
import com.sa46lll.jpareal.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("items/new")
    public String createItem(@Valid BookForm form, BindingResult result) {
        Book book = Book.of(form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());
        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);

        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm(item.getId(), item.getName(), item.getPrice(),
                item.getStockQuantity(), item.getAuthor(), item.getIsbn()
        );

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm bookForm, @PathVariable Long itemId) {
        // 변경감지 버전
        itemService.changeBook(itemId, UpdateItemRequest.of(bookForm));

        // merge 버전
        /*
        Book book = Book.updateBook(bookForm.getId(), bookForm.getName(), bookForm.getPrice(),
                bookForm.getStockQuantity(),bookForm.getAuthor(), bookForm.getIsbn());
        book.setId(itemId);

        itemService.saveItem(book);
         */

        return "redirect:/items";
    }
}
