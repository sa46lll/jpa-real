package com.sa46lll.jpareal.item.domain;

import com.sa46lll.jpareal.item.dto.UpdateItemRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item {

    private String author;
    private String isbn;

    public static Book of(String name, int price, int quantity, String author, String isbn) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        return book;
    }

    public void changeBook(UpdateItemRequest updateItemRequest) {
        super.setName(updateItemRequest.getName());
        super.setPrice(updateItemRequest.getPrice());
        super.setStockQuantity(updateItemRequest.getStockQuantity());
        this.author = updateItemRequest.getAuthor();
        this.isbn = updateItemRequest.getIsbn();

    }

    // merge 방식
    public static Book updateBook(Long id, String name, int price, int quantity, String author, String isbn) {
        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        return book;
    }
}
