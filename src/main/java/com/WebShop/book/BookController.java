package com.WebShop.book;

import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @GetMapping
    @CrossOrigin
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @PostMapping
    @CrossOrigin
    public Book addbook(@RequestBody Book book) {
        bookRepository.save(book);
        return book;
    }

    @GetMapping("/{isbn}")
    @CrossOrigin
    public Optional<Book> getBook(@PathVariable int isbn) {

        return bookRepository.findById(isbn);
    }

    @DeleteMapping("/{isbn}")
    @CrossOrigin
    public void deleteBook(@PathVariable int isbn) {
        bookRepository.deleteById(isbn);
    }

    @PutMapping()
    @CrossOrigin
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
    @CrossOrigin
    public void exampleBase () {

        Book[] books = {
                new Book(BooksCategory.CHESS.categoryName, "Agresywne szachy", "Long long time ago", "Kasparov",10,29.99),
                new Book(BooksCategory.CHESS.categoryName, "Obrona francuska", "Słaby debiut ale zawsze cos", "Karpov",30,99.99),
                new Book(BooksCategory.CHESS.categoryName,"Obrona Holenderska", "Sam go uzywam", "Majdan",5,50.99),
                new Book(BooksCategory.DRAMA.categoryName,"Romeo i Julia", "Opowieść o pięknej miłości", "William Shekspire", 10, 120.55),
                new Book(BooksCategory.COOK.categoryName,"Gotuj razem z nami", "Domowe przepisy", "Gal Anonim", 3, 26.00),
                new Book(BooksCategory.HISTORY.categoryName, "Dawno dawno temu w lesie", "Historia małego liska", "Jan Brzechwa", 9, 6.99)
        };

        Arrays.stream(books)
                .forEach(bookRepository::save);

    }
}


