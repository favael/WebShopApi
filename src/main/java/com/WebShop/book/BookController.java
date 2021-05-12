package com.WebShop.book;

import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

    @GetMapping("/szachy")
    @CrossOrigin
    public Stream<Book> getChessBooks() {
        return bookRepository.findAll().stream()
                .filter(b -> b.getBooksCategory().equals(BooksCategory.CHESS.categoryName));
    }

    @GetMapping("/romans")
    @CrossOrigin
    public Stream<Book> getRomanticBooks() {
        return bookRepository.findAll().stream()
                .filter(b -> b.getBooksCategory().equals(BooksCategory.ROMANCE.categoryName));
    }
    @GetMapping("/geografia")
    @CrossOrigin
    public Stream<Book> getGeographyBooks() {
        return bookRepository.findAll().stream()
                .filter(b -> b.getBooksCategory().equals(BooksCategory.GEOGRAPHY.categoryName));
    }

    @GetMapping("/dramat")
    @CrossOrigin
    public Stream<Book> getDramaBooks() {
        return bookRepository.findAll().stream()
                .filter(b -> b.getBooksCategory().equals(BooksCategory.DRAMA.categoryName));
    }
    @GetMapping("/scfi")
    @CrossOrigin
    public Stream<Book> getScfiBooks() {
        return bookRepository.findAll().stream()
                .filter(b -> b.getBooksCategory().equals(BooksCategory.SCIFI.categoryName));
    }
    @GetMapping("/gotowanie")
    @CrossOrigin
    public Stream<Book> getCookBooks() {
        return bookRepository.findAll().stream()
                .filter(b -> b.getBooksCategory().equals(BooksCategory.COOK.categoryName));
    }

    @GetMapping("/historia")
    @CrossOrigin
    public Stream<Book> getHistroryBooks() {
        return bookRepository.findAll().stream()
                .filter(b -> b.getBooksCategory().equals(BooksCategory.HISTORY.categoryName));
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
                new Book(BooksCategory.CHESS.categoryName, "Agresywne szachy", "Dobrze się stało, że wydawnictwo „Penelopa” sięgnęło po tłumaczenia wartościowych książek, które są tak potrzebne polskim szachistom. Autorem przedstawionej pracy jest duński arcymistrz mieszkający na stałe w Szkocji. Jacob Aagaard omawia w niej różne problemy prowadzenia ataku w partii szachowej. Jest to więc doskonały podręcznik dla tych, co chcą poznać bliżej ważne motywy taktyczne najczęściej spotykane w praktyce turniejowej. Zawodnicy bardziej zaawansowani mogą natomiast pogłębić wiedzę o kombinacyjnej grze. Oczywiście szkoleniowcy mają tutaj gotowy materiał do zajęć z młodzieżą.\n" +
                        "\n" +
                        "Tematyczny podział książki:\n" +
                        "Zmasowanymi siłami do ataku (Rozdział pierwszy)\n" +
                        "Nie tracić pary (Rozdział drugi)\n" +
                        "Dodać nieco koloru do swojej gry (Rozdział trzeci)\n" +
                        "Względna wartość figur się liczy! (Rozdział czwarty)\n" +
                        "Uderz tam, gdzie zaboli (Rozdział piąty)\n" +
                        "Gryząc glebę (Rozdział szósty)\n" +
                        "Ewolucja/rewolucja (Rozdział siódmy)\n" +
                        "15 wielkich przykładów ataku (Rozdział ósmy)\n" +
                        "Nie zapomnij zrobić następnego kroku (Rozdział dziewiąty)\n" +
                        "\n" +
                        "Każdy rozdział rozpoczyna się ćwiczeniami wprowadzającymi." +

                        "Każdy rozdział rozpoczyna się ćwiczeniami wprowadzającymi.", "Kasparov",10, 29.99, "agresywne_szachy.jpeg"),
                new Book(BooksCategory.CHESS.categoryName, "Obrona francuska", "Słaby debiut ale zawsze cos", "Karpov",30, 29.99, "french.jpeg"),
                new Book(BooksCategory.CHESS.categoryName,"Obrona Holenderska", "Sam go uzywam", "Majdan",5,50.99, "dutch.jpeg"),
                new Book(BooksCategory.CHESS.categoryName,"Mój System", "Bestseller!! ", "Nimzowitsch",11,90.99, "moj system.jpeg"),
                new Book(BooksCategory.CHESS.categoryName,"Obrona Sycylijska", "Bestseller!! ", "Damazy Sowiecki",2,40.99, "obrona sycylijska.jpeg"),
                new Book(BooksCategory.CHESS.categoryName,"Obrona Skandynawska", "Nie ma żadnych wątpliwości, że podstawą sukcesu w każdej partii szachowej jest pomyślny start.", "Jerzy  Konikowski",9,66.99, "obrona skandynawska.jpeg"),
                new Book(BooksCategory.ROMANCE.categoryName,"Romeo i Julia", "Opowieść o pięknej miłości", "William Shakespeare", 10, 120.55, "romeo.jpeg"),
                new Book(BooksCategory.COOK.categoryName,"Gotuj z nami", "Domowe przepisy", "Ewa Chodakowska gotuje ", 3, 26.00, "gotuj z nami.jpeg"),
                new Book(BooksCategory.HISTORY.categoryName, "Kłopoty  małego liska", "Lisek ma przerabane", "Wioletta Święcińska", 9, 6.99, "lisek.jpeg")
        };

        Arrays.stream(books)
                .forEach(bookRepository::save);

    }
}


