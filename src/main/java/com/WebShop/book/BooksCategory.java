package com.WebShop.book;

public enum BooksCategory {
    CHESS ("Szachy"),
    GEOGRAPHY("Geografia"),
    HISTORY("Historia"),
    COOK("Gotowanie"),
    ROMANCE("Romans"),
    SCIFI("Sci-Fi & Fantasy"),
    DRAMA("Dramat");

    String categoryName;

    BooksCategory(String categoryName) {
        this.categoryName = categoryName;
    }
}
