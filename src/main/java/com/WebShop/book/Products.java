package com.WebShop.book;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Produkty")

public class Products implements Serializable {
    @Id
    private int id;
    @Embedded
    private List<Book> bookList;

    public Products(int id, List<Book> bookList, List<ProductsCategory> productsCategoryList) {
        this.id = id;
        this.bookList = bookList;
    }

    public Products() {
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
