package com.WebShop.book;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Embeddable
public class Book implements Serializable {

    @Id
    private int isbn;
    private final BooksCategory booksCategory = BooksCategory.CHESS;
    private String title;
    private String description;
    private String author;
    private int quantity;
    private double prize;


    public Book(String title, String description, int isbn, String author, int quantity, double prize) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.author = author;
        this.quantity = quantity;
        this.prize = prize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsbn() {
        return isbn;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrize() {
        return prize;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public Book() {
    }
}
