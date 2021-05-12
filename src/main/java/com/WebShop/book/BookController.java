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
                new Book(BooksCategory.CHESS.categoryName, "Agresywne szachy", "Dobrze się stało, że wydawnictwo „Penelopa” sięgnęło po tłumaczenia wartościowych książek, które są tak potrzebne polskim szachistom. Autorem przedstawionej pracy jest duński arcymistrz mieszkający na stałe w Szkocji. Jacob Aagaard omawia w niej różne problemy prowadzenia ataku w partii szachowej. Jest to więc doskonały podręcznik dla tych, co chcą poznać bliżej ważne motywy taktyczne najczęściej spotykane w praktyce turniejowej. Zawodnicy bardziej zaawansowani mogą natomiast pogłębić wiedzę o kombinacyjnej grze. Oczywiście szkoleniowcy mają tutaj gotowy materiał do zajęć z młodzieżą. Tematyczny podział książki: Zmasowanymi siłami do ataku (Rozdział pierwszy) Nie tracić pary (Rozdział drugi) Dodać nieco koloru do swojej gry (Rozdział trzeci) Względna wartość figur się liczy! (Rozdział czwarty) Uderz tam, gdzie zaboli (Rozdział piąty) Gryząc glebę (Rozdział szósty) Ewolucja/rewolucja (Rozdział siódmy) 15 wielkich przykładów ataku (Rozdział ósmy) Nie zapomnij zrobić następnego kroku (Rozdział dziewiąty),Każdy rozdział rozpoczyna się ćwiczeniami wprowadzającymi. Każdy rozdział rozpoczyna się ćwiczeniami wprowadzającymi.", "Kasparov Garry",10, 29.99, "agresywne_szachy.jpeg"),
                new Book(BooksCategory.CHESS.categoryName, "Obrona francuska", "Wiktor Korcznoj, Aleksander Kalinin Dwukrotny wicemistrz świata Wiktor Korcznoj napisał książkę swojego życia. Zawarł w niej wyniki blisko siedemdziesięciu lat studiowania i grania jednego z najpopularniejszych debiutów. Książka zawiera 72 przeanalizowane partie, kluczowe dla zrozumienia tego trudnego debiutu. Zamiast wkuwania na pamięć wariantów , autorzy proponują najważniejsze plany dla obu stron. Pozycja niezbędna w bibliotece każdego szachisty, grającego obronę francuską czarnymi lub zaczyna partię 1. e2-e4", "Karpov Anatoly",30, 29.99, "french.jpeg"),
                new Book(BooksCategory.CHESS.categoryName,"Obrona Holenderska", "Do you prefer to avoid symmetrical games against white’s 1. d4? And do sharp and complex positions not frighten you? If the answer is yes, the Modernized Dutch Defense might be a worthy surprise weapon to add to your opening repertoire!", "Majdan",5,50.99, "dutch.jpeg"),
                new Book(BooksCategory.CHESS.categoryName,"Mój System", "W naszej ofercie nie może zabraknąć tej pozycji, rzetelne przestudiowanie tej książki na pewno spowoduje podniesienie siły gry i zrozumienia szachów. Uważana przez wielu czołowych szachistów świata jako jedna z najważniejszych książek w historii szachów - ścisłe TOP 10 literatury szachowej. W książce poruszono wiele ciekawych tematów dających inne spojrzenie: wolny pion , lawirowanie, wymiana i łańcuch pionów. Szachowy elementarz strategii i gry pozycyjnej,napisany z dużym poczuciem humoru. Najbardziej znana książka szachowa na świecie. Na temat tej książki już wszystko zostało napisane. Przeczytaj recenzję Pana Jerzego Konikowskiego Mój system Aron Nimzowitsch. Ilość stron: 456 Okładka: miękka Format książki: 16 x 23 cm ", "Nimzowitsch Aron",11,90.99, "moj system.jpeg"),
                new Book(BooksCategory.CHESS.categoryName,"Obrona Sycylijska", "Wariant Richtera-Rauzera w obronie sycylijskiej znajduje się w bogatym repertuarze debiutowym najwybitniejszych szachistów świata XX i XXI wieku.Książka powstała z myślą o początkujących szachistach, którzy nie od razu muszą wiedzieć wszystko. Analizuje kontynuacje, które, zdaniem autora, są najważniejsze.W pierwszej części książki omawiane są poszczególne posunięcia. W drugiej - zaprezentowane są przykładowe partie. Całość, dla łatwiejszego poruszania się po poszczególnych wariantach, poprzedzono diagramami.", "Damazy Sowiecki",2,40.99, "obrona sycylijska.jpeg"),
                new Book(BooksCategory.CHESS.categoryName,"Obrona Skandynawska", "Nie ma żadnych wątpliwości, że podstawą sukcesu w każdej partii szachowej jest pomyślny start.", "Jerzy  Konikowski",9,66.99, "obrona skandynawska.jpeg"),
                new Book(BooksCategory.DRAMA.categoryName,"Romeo i Julia", "Opowieść o pięknej miłości", "William Shakespeare", 10, 120.55, "romeo.jpeg"),
                new Book(BooksCategory.COOK.categoryName,"Gotuj z nami", "Domowe przepisy", "Ewa Chodakowska", 3, 26.00, "gotuj z nami.jpeg"),
                new Book(BooksCategory.HISTORY.categoryName, "Kłopoty  małego liska", "Lisek ma przerabane", "Wioletta Święcińska", 9, 6.99, "lisek.jpeg"),
                new Book(BooksCategory.SCIFI.categoryName, "Solar War", "Explore the final stages of The Horus Heresy in this fantastic series, a must have for all fans! New York Times Bestselling Series. After seven years of bitter war, the end has come at last for the conflict known infamously as the Horus Heresy. Terra now lies within the Warmaster’s sights, the Throneworld and the seat of his father’s rule. Horus’ desire is nothing less than the death of the Emperor of Mankind and the utter subjugation of the Imperium. He has become the ascendant vessel of Chaos, and amassed a terrible army with which to enact his will and vengeance. But the way to the Throne will be hard as the primarch Rogal Dorn, the Praetorian and protector of Terra, marshals the defences. First and foremost, Horus must challenge the might of the Sol System itself and the many fleets and bulwarks arrayed there. To gain even a foothold on Terran soil, he must first contend the Solar War. Thus the first stage of the greatest conflict in the history of all mankind begins.", "John French", 10, 78.99, "solar war.jpeg"),
                new Book(BooksCategory.SCIFI.categoryName, "Diuna", "Arrakis, zwana Diuną, to jedyne we wszechświecie źródło melanżu. Z rozkazu Padyszacha Imperatora planetę przejmują Atrydzi, zaciekli wrogowie władających nią dotychczas Harkonnenów. Zwycięstwo księcia Leto Atrydy jest jednak pozorne – przejęcie planety ukartowano. W odpowiedzi na atak Imperium i Harkonnenów dziedzic rodu Atrydów Paul staje na czele rdzennych mieszkańców Diuny i sięga po imperialny tron. Oszałamiające połączenie przygody i mistycyzmu, ekologii i polityki. „Diuna” otrzymała dwie najbardziej prestiżowe nagrody SF: Nebula i Hugo.", "Herbert Frank", 21, 23.46, "diuna.jpeg")
        };

        Arrays.stream(books)
                .forEach(bookRepository::save);

    }
}


