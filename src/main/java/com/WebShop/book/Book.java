package com.WebShop.book;

import javax.persistence.*;

@Entity
public class Book  {

    @Id
    @GeneratedValue
    private int isbn;
    private String title;
    private String booksCategory;
    private String description;
    private String author;
    private int quantity;
    private double prize;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Book(String booksCategory, String title, String description, String author, int quantity, double prize, String url) {
        this.title = title;
        this.booksCategory = booksCategory;
        this.description = description;
        this.author = author;
        this.quantity = quantity;
        this.prize = prize;
        this.url = url;
    }

    public int getIsbn() {
        return isbn;
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

    public Book() {
    }
}
