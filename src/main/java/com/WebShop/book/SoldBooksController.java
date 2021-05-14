package com.WebShop.book;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/soldBook")
public class SoldBooksController {
private final SoldBookRepository soldBookRepository;

    public SoldBooksController(SoldBookRepository soldBookRepository) {
        this.soldBookRepository = soldBookRepository;
    }


    @PostMapping("/shoppingCardList")
    @CrossOrigin
    public void addToCardList(@RequestBody SoldBook book) {
        soldBookRepository.save(book);
    }
    @GetMapping("/shoppingCardList")
    @CrossOrigin
    public List<SoldBook> getShoppingCardList() {
        return soldBookRepository.findAll();
    }
}
