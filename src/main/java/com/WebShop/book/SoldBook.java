package com.WebShop.book;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
@Entity
public class SoldBook {
    @Id
    @GeneratedValue
    private int isbn;
    private String title;
    private String booksCategory;
    @Size(max=10000)
    private String description;
    private String author;
    private int quantity;
    private double prize;
    private String url;

    public SoldBook(int isbn, String title, String booksCategory, String description, String author, int quantity, double prize, String url) {
        this.isbn = isbn;
        this.title = title;
        this.booksCategory = booksCategory;
        this.description = description;
        this.author = author;
        this.quantity = quantity;
        this.prize = prize;
        this.url = url;
    }

    public SoldBook() {
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBooksCategory() {
        return booksCategory;
    }

    public void setBooksCategory(String booksCategory) {
        this.booksCategory = booksCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
