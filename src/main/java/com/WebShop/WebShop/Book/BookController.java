package com.WebShop.WebShop.Book;

import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @GetMapping
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @PostMapping
    public Book addbook(@RequestBody Book book) {
        bookRepository.save(book);
        return book;
    }

    @GetMapping("/{isbn}")
    public Optional<Book> getBook(@PathVariable int isbn) {

        return bookRepository.findById(isbn);
    }

    @DeleteMapping("/{isbn}")
    public void deleteBook(@PathVariable int isbn) {
        bookRepository.deleteById(isbn);
    }

    @PutMapping()
    public Book updateBook(@RequestBody Book book) {

        Book updateBook = bookRepository.getOne(book.getIsbn());
        book.setQuantity(updateBook.getQuantity());
        book.setAuthor(updateBook.getAuthor());
        book.setDescription(updateBook.getDescription());
        book.setTitle(updateBook.getTitle());
        book.setPrize(updateBook.getPrize());
        bookRepository.save(updateBook);
        return updateBook;
    }
    @PostConstruct
    public void exampleBase () {

        Book[] books = {
                new Book("Agresywne szachy","Long long time ago", 1, "Kasparov",10,29.99),
                new Book("Obrona Francuzka","Slaby debiut ale zawsze cos", 2, "Karpov",30,99.99),
                new Book("Obrona Holenderska","Sam go uzywam", 3, "Majdan",5,50.99)
        };

        Arrays.stream(books)
                .forEach(bookRepository::save);

    }
}


