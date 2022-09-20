package com.sa46lll.jpareal.item.dto;

import com.sa46lll.jpareal.item.controller.BookForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateItemRequest {
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

    public UpdateItemRequest(String name, int price, int stockQuantity, String author, String isbn) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }

    public static UpdateItemRequest of(BookForm bookForm) {
        return new UpdateItemRequest(bookForm.getName(), bookForm.getPrice(),
                bookForm.getStockQuantity(), bookForm.getAuthor(), bookForm.getIsbn());
    }
}
